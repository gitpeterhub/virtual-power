package com.pkumal.virtualpower.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkumal.virtualpower.request.BatteryRequest;
import com.pkumal.virtualpower.response.BatteryResponse;
import com.pkumal.virtualpower.service.BatteryService;

@RestController
@RequestMapping("/virtualpower/api/v1")
public class VirtualPowerApiController {

	@Autowired
	private BatteryService batteryService;

	@GetMapping("/getBatteries/{postCodeRange}")
	public BatteryResponse getBatteriesForRange(@PathVariable String postCodeRange) {
		return batteryService.findByPostcodes(postCodeRange);
	}

	@PostMapping("/addBatteries")
	public void addBatteries(@RequestBody List<BatteryRequest> batteryRequests) {
		batteryService.processBatteries(batteryRequests);
	}
}
