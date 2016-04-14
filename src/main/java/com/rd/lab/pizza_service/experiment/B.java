package com.rd.lab.pizza_service.experiment;

public class B {
	private A ai;

	public B(A ai) {
		this.ai = ai;
	}

	public void init() {
		System.out.println("init from B");
		ai.init();
	}
}