package com.rd.lab.pizza_service.experiment;

import org.springframework.stereotype.Component;

@Component
public class A0 implements I3, I1, I2 {

	@Override
	public A0 methI1() {
		return new A0();
	}

	@Override
	public void methI3() {
		System.out.println("methI3");
	}
}