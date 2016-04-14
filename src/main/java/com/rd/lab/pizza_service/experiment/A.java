package com.rd.lab.pizza_service.experiment;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.infrastructure.anno.Benchmark;

@Component("a")
public class A extends A0 implements I13 {

	public void init() {
		System.out.println("init meth form A");
	}

	// @Benchmark
	public void methA() {
		System.out.println("methA");
	}

	public void methB() {
		System.out.println("methB");
	}

	@Benchmark
	@Lookup
	@Override
	public A0 methI1() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("methI1");
		return null;
	}

	@Override
	public void methI11() {
		System.out.println("methI11");

	}

	// @Benchmark
	@Override
	public void methI12() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("methI12");
	}

	// @Benchmark
	@Override
	public void methI13() {
		System.out.println("methI13");
	}

	@Override
	public String toString() {
		return "A";
	}

}
