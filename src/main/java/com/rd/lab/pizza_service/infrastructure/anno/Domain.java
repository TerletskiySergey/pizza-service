package com.rd.lab.pizza_service.infrastructure.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Scope("prototype")
public @interface Domain {
}