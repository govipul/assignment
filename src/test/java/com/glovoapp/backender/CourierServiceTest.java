package com.glovoapp.backender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.service.CourierService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { API.class })
class CourierServiceTest {

	private static List<Order> orderList;
	private static Courier courierBoyWithBox;
	private static Courier courierBoyWithoutBox;
	private static CourierService courierService;

	@BeforeAll
	public static void init() {
		orderList = new ArrayList<>();
		orderList.add(new Order().withDescription("I want a pizza"));
		orderList.add(new Order().withDescription("I want a awesome cake"));
		orderList.add(new Order().withDescription("I want a cold drink"));
		courierBoyWithBox = new Courier().withBox(true);
		courierBoyWithoutBox = new Courier().withBox(false);
		courierService = new CourierService();
	}

	@Test
	void testGetOrdersAsPerBoxPresenceWithBox() {
		int actualSize = courierService.filterDataAsPerCriteria(courierBoyWithBox, orderList).size();
		assertEquals(3, actualSize);
	}

	@Test
	void testGetOrdersAsPerBoxPresenceWithoutBox() {
		int actualSize = courierService.filterDataAsPerCriteria(courierBoyWithoutBox, orderList).size();
		assertEquals(1, actualSize);

	}

}
