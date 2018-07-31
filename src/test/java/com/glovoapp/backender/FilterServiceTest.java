package com.glovoapp.backender;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.repository.CourierRepository;
import com.glovoapp.backender.repository.OrderRepository;
import com.glovoapp.backender.service.FilterService;

class FilterServiceTest {

	private static Order cakeOrder;
	private static Order pizzaOrder;
	private static Order flamingoOrder;
	private static Courier courierBoyWithBox;
	private static Courier courierBoyWithoutBox;
	private static OrderRepository orderRepo;
	private static CourierRepository courierRepo;

	@BeforeAll
	public static void init() {
		cakeOrder = new Order().withDescription("Cake, for my daughter!");
		pizzaOrder = new Order().withDescription("I love pizza!");
		flamingoOrder = new Order().withDescription("Flamingo, do i really need them?");
		courierBoyWithBox = new Courier().withBox(true);
		courierBoyWithoutBox = new Courier().withBox(false);
		orderRepo = new OrderRepository();
		courierRepo = new CourierRepository();
	}

	@Test
	void testIsOrderContainCake() {
		boolean cakeOrderValidity = FilterService.isOrderContainPizzaCakeFlamingo(cakeOrder, courierBoyWithBox);
		assertTrue(cakeOrderValidity);
	}

	@Test
	void testIsOrderContainPizza() {
		boolean pizzaOrderValidity = FilterService.isOrderContainPizzaCakeFlamingo(pizzaOrder, courierBoyWithBox);
		assertTrue(pizzaOrderValidity);
	}

	@Test
	void testIsOrderContainFlamingo() {
		boolean flamingoOrderValidity = FilterService.isOrderContainPizzaCakeFlamingo(flamingoOrder, courierBoyWithBox);
		assertTrue(flamingoOrderValidity);
	}

	@Test
	void testIsOrderContainCakeWithoutBox() {
		boolean cakeOrderValidity = FilterService.isOrderContainPizzaCakeFlamingo(cakeOrder, courierBoyWithoutBox);
		assertFalse(cakeOrderValidity);
	}

	@Test
	void testIsOrderContainPizzaWithoutBox() {
		boolean pizzaOrderValidity = FilterService.isOrderContainPizzaCakeFlamingo(pizzaOrder, courierBoyWithoutBox);
		assertFalse(pizzaOrderValidity);
	}

	@Test
	void testIsOrderContainFlamingoWithoutBox() {
		boolean flamingoOrderValidity = FilterService.isOrderContainPizzaCakeFlamingo(flamingoOrder,
				courierBoyWithoutBox);
		assertFalse(flamingoOrderValidity);
	}

	@Test
	void testIsOrderwithinRangeWithinRange() {
		Order order = orderRepo.findAll().get(0);
		Courier courier = courierRepo.findAll().get(0);
		boolean orderwithinRange = FilterService.isOrderwithinRange(order, courier, 50);
		assertTrue(orderwithinRange);
	}

	@Test
	void testIsOrderwithinRangeOutsideRange() {
		Order order = orderRepo.findAll().get(0);
		Courier courier = courierRepo.findAll().get(1);
		boolean orderwithinRange = FilterService.isOrderwithinRange(order, courier, 50);
		assertFalse(orderwithinRange);
	}

	@Test
	void testIsOrderwithinRangeOutsideRangeWithVehicle() {
		Order order = orderRepo.findAll().get(0);
		Courier courier = courierRepo.findAll().get(2);
		boolean orderwithinRange = FilterService.isOrderwithinRange(order, courier, 50);
		assertTrue(orderwithinRange);
	}
}
