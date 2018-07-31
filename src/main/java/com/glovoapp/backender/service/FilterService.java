package com.glovoapp.backender.service;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Location;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.model.Vehicle;
import com.glovoapp.backender.util.DistanceCalculator;

public class FilterService {

	public static boolean isOrderContainPizzaCakeFlamingo(Order order, Courier courier) {
		String orderDescription = order.getDescription().toLowerCase();
		if (orderDescription.contains("pizza") || orderDescription.contains("cake")
				|| orderDescription.contains("flamingo")) {
			return courier.getBox();
		}
		return true;

	}

	public static boolean isOrderwithinRange(Order order, Courier courier, int range) {
		Location pickupLocation = order.getPickup();
		Location deliveryLocation = order.getDelivery();
		Location courierBoyLocation = courier.getLocation();
		double pickupDistance = DistanceCalculator.calculateDistance(pickupLocation, courierBoyLocation);
		double dropDistance = DistanceCalculator.calculateDistance(pickupLocation, deliveryLocation);
		if (pickupDistance > range || dropDistance > range) {
			return (courier.getVehicle().equals(Vehicle.MOTORCYCLE)
					|| courier.getVehicle().equals(Vehicle.ELECTRIC_SCOOTER));
		}
		return true;
	}

}
