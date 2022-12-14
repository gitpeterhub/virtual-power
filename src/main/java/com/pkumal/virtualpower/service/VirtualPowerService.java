package com.pkumal.virtualpower.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pkumal.virtualpower.modal.Battery;
import com.pkumal.virtualpower.repository.BatteryRepository;
import com.pkumal.virtualpower.request.BatteryRequest;
import com.pkumal.virtualpower.response.BatteryResponse;
import com.pkumal.virtualpower.utilities.Utilities;

@Service
public class VirtualPowerService {

	@Autowired
	private BatteryRepository batteryRepository;

	public List<Battery> processBatteries(List<BatteryRequest> batteryRequests) {

		List<Battery> batteries = batteryRequests.stream()
				.map(batteryRequest -> Utilities.parseObject(batteryRequest, Battery.class))
				.collect(Collectors.toList());

		Iterable<Battery> batteriesSaved = batteryRepository.saveAll(batteries);

		return (List<Battery>) batteriesSaved;

	};

	public BatteryResponse findByPostCodes(String postCodeFrom, String postCodeTo) {

		// step 1 : get batteries within rage.
		List<Battery> batteries = batteryRepository.findByPostCodes(postCodeFrom, postCodeTo);

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
