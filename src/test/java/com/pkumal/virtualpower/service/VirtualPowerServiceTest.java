package com.pkumal.virtualpower.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.pkumal.virtualpower.modal.Battery;
import com.pkumal.virtualpower.repository.BatteryRepository;
import com.pkumal.virtualpower.request.BatteryRequest;
import com.pkumal.virtualpower.response.BatteryResponse;
import com.pkumal.virtualpower.utilities.Utilities;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class VirtualPowerServiceTest {

	@InjectMocks
	VirtualPowerService virtualPowerService;

	@Mock
	BatteryRepository batteryRepository;

	List<BatteryRequest> batteryRequests;

	List<Battery> responseBatteries;

	List<Battery> postCodeBatteriesResponse;
	BatteryResponse batteryResponse;

	@BeforeAll
	void init() {
		batteryRequests = Arrays.asList(new BatteryRequest("Cannington", 6107, 13500),
				new BatteryRequest("Midland", 6057, 50500), new BatteryRequest("Hay Street", 6000, 23500),
				new BatteryRequest("Mount Adams", 6525, 12000), new BatteryRequest("Koolan Island", 6733, 10000),
				new BatteryRequest("Armadale", 6992, 25000), new BatteryRequest("Lasmurdie", 6076, 13500),
				new BatteryRequest("Kalamunda", 6076, 13500), new BatteryRequest("Carmel", 6076, 36000),
				new BatteryRequest("Bently", 6102, 85000), new BatteryRequest("Akunda Bay", 2084, 13500),
				new BatteryRequest("Werrington County", 2747, 13500),
				new BatteryRequest("University of Melbourne", 1010, 85000),
				new BatteryRequest("Norfolk Island", 2899, 13500), new BatteryRequest("Ootha", 2875, 13500),
				new BatteryRequest("Kent Town", 5067, 13500), new BatteryRequest("Northgate Mc", 9464, 13500),
				new BatteryRequest("Gold Coast Mc", 9729, 50000));

		responseBatteries = batteryRequests.stream()
				.map(batteryRequest -> Utilities.parseObject(batteryRequest, Battery.class))
				.collect(Collectors.toList());

		List<BatteryRequest> postCodeBatteryRequests = Arrays.asList(new BatteryRequest("Cannington", 6107, 13500),
				new BatteryRequest("Midland", 6057, 50500), new BatteryRequest("Hay Street", 6000, 23500),
				new BatteryRequest("Mount Adams", 6525, 12000), new BatteryRequest("Koolan Island", 6733, 10000),
				new BatteryRequest("Armadale", 6992, 25000), new BatteryRequest("Lasmurdie", 6076, 13500),
				new BatteryRequest("Kalamunda", 6076, 13500), new BatteryRequest("Carmel", 6076, 36000),
				new BatteryRequest("Bently", 6102, 85000), new BatteryRequest("Northgate Mc", 9464, 13500),
				new BatteryRequest("Gold Coast Mc", 9729, 50000));

		postCodeBatteriesResponse = postCodeBatteryRequests.stream()
				.map(batteryRequest -> Utilities.parseObject(batteryRequest, Battery.class))
				.collect(Collectors.toList());

		batteryResponse = new BatteryResponse();

		List<String> responseNames = Arrays.asList("Armadale", "Bently", "Cannington", "Carmel", "Gold Coast Mc",
				"Hay Street", "Kalamunda", "Koolan Island", "Lasmurdie", "Midland", "Mount Adams", "Northgate Mc");
		batteryResponse.setBatteryNames(responseNames);
		batteryResponse.setAverageWatts(28538.46153846154);
		batteryResponse.setTotalWatts(371000);
	}

	@Test
	void testAddListOfBatteries() {

		Mockito.when(batteryRepository.saveAll(Mockito.any())).thenReturn(responseBatteries);
		assertEquals(18, virtualPowerService.processBatteries(batteryRequests).size());

	}

	@Test
	void testGetBatteriesForPostCodeRange() {

		Mockito.when(batteryRepository.findByPostCodes("6000", "10000")).thenReturn(postCodeBatteriesResponse);
		BatteryResponse batteryResponse = virtualPowerService.findByPostCodes("6000", "10000");
		assertEquals(12, batteryResponse.getBatteryNames().size());
		assertTrue(batteryResponse.getAverageWatts() > 0);
		assertTrue(batteryResponse.getTotalWatts() > 0);

	}

}
