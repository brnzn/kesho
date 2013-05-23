package com.test;

import org.springframework.beans.factory.annotation.Autowired;

public class SampleController {
	@Autowired
	private OldPerson person;

	public OldPerson getPerson() {
		return person;
	}

	public void print() {
		System.out.println("Well done, " + person.getFirstName() + "!");
	}
}