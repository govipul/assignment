package com.glovoapp.backender.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.util.DistanceCalculator;

@Service
public class SortService {

	@Value("${backender.order.sort: DISTANCE,VIP,FOOD}")
	private String[] sortOrders;

	public void setSortOrders(String[] sortOrders) {
		this.sortOrders = sortOrders;
	}

	public void sortOrder(Courier courier, List<Order> orderList) {

		for (String sortOrder : sortOrders) {
			switch (sortOrder.toUpperCase()) {
			case "DISTANCE":
				Collections.sort(orderList, sortByLocation(courier, orderList));
				break;
			case "VIP":
				Collections.sort(orderList, sortByVIP(courier, orderList));
				break;

			case "FOOD":
				Collections.sort(orderList, sortByFood(courier, orderList));
				break;
			}
		}

	}

	private Comparator<Order> sortByLocation(Courier courier, List<Order> orderList) {
		Comparator<Order> byLocation = (Order o1, Order o2) -> {
			return Double.compare(DistanceCalculator.calculateDistance(courier.getLocation(), o1.getPickup()),
					DistanceCalculator.calculateDistance(courier.getLocation(), o2.getPickup()));
		};

		return byLocation;
	}

	private Comparator<Order> sortByVIP(Courier courier, List<Order> orderList) {
		return Comparator.comparing(Order::getVip);
	}

	private Comparator<Order> sortByFood(Courier courier, List<Order> orderList) {
		return Comparator.comparing(Order::getFood);
	}

}
