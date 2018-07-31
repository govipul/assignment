package com.glovoapp.backender.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;

@Service
public class CourierService {

	@Value("${backender.range:5}")
	private int range;

	@Value("${backender.sort.priority:distance,vip,food}")
	private String[] sortingPriorities;

	public int getRange() {
		return range;
	}

	// public List<Order> getOrdersAsPerBoxPresence(Courier courier, List<Order>
	// orderList) {
	// return orderList.stream().filter(order ->
	// FilterService.isOrderContainPizzaCakeFlamingo(order, courier))
	// .collect(Collectors.toList());
	// }

	public List<Order> filterDataAsPerCriteria(Courier courier, List<Order> orderList) {
		return orderList.stream().filter(order -> FilterService.isOrderContainPizzaCakeFlamingo(order, courier))
				.filter(order -> FilterService.isOrderwithinRange(order, courier, range)).collect(Collectors.toList());
	}

}
