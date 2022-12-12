package com.pkumal.virtualpower.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkumal.virtualpower.modal.Battery;
import com.pkumal.virtualpower.repository.BatteryRepository;
import com.pkumal.virtualpower.request.BatteryRequest;
import com.pkumal.virtualpower.response.BatteryResponse;

@Service
public class BatteryService {

	@Autowired
	private BatteryRepository batteryRepository;

	public void processBatteries(List<BatteryRequest> batteryRequests) {

		ObjectMapper objectMapper = new ObjectMapper();
		/**
		 * 
		 * Make json key case insensitive for mapping to target class eg: json key is
		 * not in camel case but target class has camel case field
		 * 
		 */
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		List<Battery> batteries = batteryRequests.stream()
				.map(batteryRequest -> objectMapper.convertValue(batteryRequest, Battery.class))
				.collect(Collectors.toList());

		batteryRepository.saveAll(batteries);

	};

	public BatteryResponse findByPostcodes(String postCodeRange) {

		// step 1 : get batteries within rage.
		String postCodeRange1 = postCodeRange.split("-")[0];
		String postCodeRange2 = postCodeRange.split("-")[1];
		
		List<Battery> batteries = batteryRepository.findByPostCodes(postCodeRange1, postCodeRange2);
		
		if (batteries.isEmpty()) {
			return new BatteryResponse();
		}

		// step 2 : sort alphabetically.

		List<String> batteryNames = batteries.stream().map(Battery::getName).sorted().collect(Collectors.toList());

		// step 3 : add additional information like : total and average capacity.

		long totalWatts = batteries.stream().map(battery -> battery.getCapacity()).mapToInt(Integer::intValue).sum();
		double averageWatts = (double) totalWatts / batteries.size();

		return new BatteryResponse(batteryNames, totalWatts, averageWatts);

	};

}
