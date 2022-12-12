package com.pkumal.virtualpower.modal;

import jakarta.persistence.Entity;

@Entity
public class Battery extends BaseModal {

	private String name;
	private int postCode;
	private int capacity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
