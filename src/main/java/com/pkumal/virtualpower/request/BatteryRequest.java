package com.pkumal.virtualpower.request;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonSetter;

public class BatteryRequest {

	private String name;
	private int postCode;
	private int capacity;

	public BatteryRequest() {
		
	}
	
	public BatteryRequest(String name, int postCode, int capacity) {
		this.name = name;
		this.postCode = postCode;
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPostCode() {
		return postCode;
	}

	/**
	 * 
	 * Make json key case insensitive for mapping to @RequestBody in controller eg: json key is
	 * not in camel case but target class mapped with @RequestBody has camel case field
	 * 
	 */
	@JsonSetter("postcode")
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public int hashCode() {

		return (name == null ? 0 : name.hashCode());

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (!(obj instanceof BatteryRequest))
			return false;
		BatteryRequest that = (BatteryRequest) obj;
		return Objects.equals(name, that.name);
	}
}
