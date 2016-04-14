package com.rd.lab.pizza_service.infrastructure.post_processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

import com.rd.lab.pizza_service.infrastructure.anno.Benchmark;

public class BenchmarkProxyBeanPostProcessor implements BeanPostProcessor {

	private static final Class<? extends Annotation> ANNOTATION_CLASS = Benchmark.class;

	private static class BenchmarkAnnoHandler implements InvocationHandler {
		static final String HANDLER_MESSAGE = "Method: %s, duration: %.3f seconds\n";
		static final double MILLIS_COEFFICIENT = 1000;

		Object bean;
		Map<String, Method> markedMeths;

		BenchmarkAnnoHandler(Object bean, Map<String, Method> markedMeths) {
			this.bean = bean;
			this.markedMeths = markedMeths;
		}

		@Override
		public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable {
			Method markedMeth = markedMeths.get(meth.getName());
			if (markedMeth != null && areMethodsEqual(markedMeth, meth)) {
				return handle(meth, args);
			}
			return meth.invoke(bean, args);
		}

		boolean areMethodsEqual(Method m1, Method m2) {
			return m1.getName().equals(m2.getName())
					&& Arrays.equals(m1.getParameterTypes(), m2.getParameterTypes())
					&& m1.getReturnType().equals(m2.getReturnType());
		}

		Object handle(Method meth, Object[] args) throws Throwable {
			long timer = System.currentTimeMillis();
			Object toReturn = meth.invoke(bean, args);
			reportDuration(System.currentTimeMillis() - timer, meth);
			return toReturn;
		}

		void reportDuration(long time, Method meth) {
			double dur = time / MILLIS_COEFFICIENT;
			System.out.printf(HANDLER_MESSAGE, meth.getName(), dur);
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		Class<?> beanClass = bean.getClass();
		while (Enhancer.isEnhanced(beanClass)) {
			beanClass = beanClass.getSuperclass();
		}
		Map<String, Method> markedMeths = getMarkedMethods(beanClass);
		if (!markedMeths.isEmpty()) {
			bean = wrap(beanClass, new BenchmarkAnnoHandler(bean, markedMeths));
		}
		return bean;
	}

	Map<String, Method> getMarkedMethods(Class<?> beanClass) {
		Map<String, Method> toReturn = new HashMap<>();
		for (Method meth : beanClass.getMethods()) {
			if (meth.getAnnotation(ANNOTATION_CLASS) != null) {
				toReturn.put(meth.getName(), meth);
			}
		}
		return toReturn;
	}

	private Object wrap(Class<?> beanClass, InvocationHandler handler) {
		Set<Class<?>> interfs = new HashSet<>();
		getAllInterfaces(beanClass, interfs);
		return Proxy.newProxyInstance(beanClass.getClassLoader(), interfs.toArray(new Class<?>[0]),
				handler);
	}

	void getAllInterfaces(Class<?> curClass, Set<Class<?>> set) {
		if (curClass == null) {
			return;
		}
		if (curClass.isInterface() && !set.contains(curClass)) {
			set.add(curClass);
		}
		getAllInterfaces(curClass.getSuperclass(), set);
		for (Class<?> clazz : curClass.getInterfaces()) {
			getAllInterfaces(clazz, set);
		}
	}
}
