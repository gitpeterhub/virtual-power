package com.pkumal.virtualpower.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pkumal.virtualpower.modal.Battery;
import com.pkumal.virtualpower.request.BatteryRequest;
import com.pkumal.virtualpower.response.BatteryResponse;
import com.pkumal.virtualpower.service.VirtualPowerService;

@RestController
@RequestMapping("/virtualpower/api/v1")
public class VirtualPowerApiController {

	@Autowired
	private VirtualPowerService virtualPowerService;

	@GetMapping("/getBatteriesByPostCodes")
	@ResponseStatus(HttpStatus.OK)
	public BatteryResponse getBatteriesForRange(@RequestParam("from") String postCodeFrom, @RequestParam("to") String postCodeTo) {
		return virtualPowerService.findByPostCodes(postCodeFrom, postCodeTo);
	}

	@PostMapping("/addBatteries")
	public ResponseEntity<List<Battery>> addBatteries(@RequestBody List<BatteryRequest> batteryRequests) {
		return ResponseEntity.ok(virtualPowerService.processBatteries(batteryRequests));
	}
}
