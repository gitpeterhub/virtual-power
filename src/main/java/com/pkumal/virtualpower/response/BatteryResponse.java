package com.pkumal.virtualpower.response;

import java.util.List;

public class BatteryResponse {

	private List<String> batteryNames;
	private long totalWatts;
	private double averageWatts;

	public BatteryResponse() {

	}

	public BatteryResponse(List<String> batteryNames, long totalWatts, double averageWatts) {
		super();
		this.batteryNames = batteryNames;
		this.totalWatts = totalWatts;
		this.averageWatts = averageWatts;
	}

	public List<String> getBatteryNames() {
		return batteryNames;
	}

	public void setBatteryNames(List<String> batteryNames) {
		this.batteryNames = batteryNames;
	}

	public long getTotalWatts() {
		return totalWatts;
	}

	public void setTotalWatts(long total) {
		this.totalWatts = total;
	}

	public double getAverageWatts() {
		return averageWatts;
	}

	public void setAverageWatts(double averageWatts) {
		this.averageWatts = averageWatts;
	}

}
