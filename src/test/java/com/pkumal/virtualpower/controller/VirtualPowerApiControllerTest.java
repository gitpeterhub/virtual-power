package com.pkumal.virtualpower.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkumal.virtualpower.modal.Battery;
import com.pkumal.virtualpower.request.BatteryRequest;
import com.pkumal.virtualpower.response.BatteryResponse;
import com.pkumal.virtualpower.service.VirtualPowerService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class VirtualPowerApiControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	VirtualPowerService virtualPowerService;

	List<BatteryRequest> batteryRequests;
	
	@Autowired
	ObjectMapper objectMapper;

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
	}

	@Test
	void testAddListOfBatteries() throws JsonProcessingException, Exception {

		MvcResult result = mockMvc
				.perform(post("/virtualpower/api/v1/addBatteries").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(batteryRequests)))
				.andExpect(status().isOk()).andReturn();

		List<Battery> batteriesSaved = objectMapper.readValue(result.getResponse().getContentAsString(),
				new TypeReference<List<Battery>>() {

				});

		assertFalse(() -> batteriesSaved.isEmpty());

	}

	@Test
	void testGetListOfBatteriesForPostcodeRange() throws JsonProcessingException, Exception {

		mockMvc.perform(post("/virtualpower/api/v1/addBatteries").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(batteryRequests))).andExpect(status().isOk());

		MvcResult result = mockMvc
				.perform(get("/virtualpower/api/v1/getBatteries/6000-10000").accept(MediaType.APPLICATION_JSON))
				.andReturn();

		BatteryResponse batteryResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
				BatteryResponse.class);

		assertAll(() -> assertNotNull(batteryResponse), () -> assertFalse(batteryResponse.getBatteryNames().isEmpty()));

	}

	@Test
	void testGetListOfBatteriesForOrderedBatteriesNames_With_WattCapacityDetails()
			throws JsonProcessingException, Exception {

		mockMvc.perform(post("/virtualpower/api/v1/addBatteries").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(batteryRequests))).andExpect(status().isOk());

		MvcResult result = mockMvc
				.perform(get("/virtualpower/api/v1/getBatteries/6000-10000").accept(MediaType.APPLICATION_JSON))
				.andReturn();

		BatteryResponse batteryResponse = objectMapper.readValue(result.getResponse().getContentAsString(),
				BatteryResponse.class);
		System.out.println("batteryResponse " + batteryResponse.getBatteryNames());
		List<String> batteryNamesSorted = batteryResponse.getBatteryNames().stream().sorted().toList();

		assertAll(() -> assertTrue(batteryResponse.getAverageWatts() > 0),
				() -> assertTrue(batteryResponse.getTotalWatts() > 0),
				() -> assertTrue(batteryResponse.getBatteryNames().stream().toList().equals(batteryNamesSorted)));

	}

}
