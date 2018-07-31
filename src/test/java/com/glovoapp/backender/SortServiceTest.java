package com.glovoapp.backender;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.repository.CourierRepository;
import com.glovoapp.backender.repository.OrderRepository;
import com.glovoapp.backender.service.SortService;

class SortServiceTest {

	private static OrderRepository orderRepo;
	private static CourierRepository courierRepo;
	private static SortService sortService;

	@BeforeAll
	public static void init() {
		orderRepo = new OrderRepository();
		courierRepo = new CourierRepository();
		sortService = new SortService();
		String[] orders = { "distance", "food", "vip" };
		sortService.setSortOrders(orders);
	}

	@Test
	void testSortOrder() {
		List<Order> allOrder = orderRepo.findAll();
		Courier courier = courierRepo.findAll().get(0);
		String[] expectedOrders = { "order-2", "order-1", "order-4", "order-3" };
		sortService.sortOrder(courier, allOrder);

		assertTrue(allOrder.get(0).getId().equals(expectedOrders[0]));
		assertTrue(allOrder.get(2).getId().equals(expectedOrders[2]));

	}

}
