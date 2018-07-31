package com.glovoapp.backender;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glovoapp.backender.exception.CourierNotFoundException;
import com.glovoapp.backender.exception.OrderNotFoundException;
import com.glovoapp.backender.model.Courier;
import com.glovoapp.backender.model.Order;
import com.glovoapp.backender.model.OrderVM;
import com.glovoapp.backender.repository.CourierRepository;
import com.glovoapp.backender.repository.OrderRepository;
import com.glovoapp.backender.service.CourierService;
import com.glovoapp.backender.service.SortService;

@Controller
@ComponentScan("com.glovoapp.backender")
@EnableAutoConfiguration
public class API {
	private final String welcomeMessage;
	private final OrderRepository orderRepository;
	private final CourierRepository courierRepository;

	@Autowired
	private CourierService courierService;
	@Autowired
	private SortService sortService;

	@Autowired
	API(@Value("${backender.welcome_message}") String welcomeMessage, OrderRepository orderRepository,
			CourierRepository courierRepository) {
		this.welcomeMessage = welcomeMessage;
		this.orderRepository = orderRepository;
		this.courierRepository = courierRepository;
	}

	@RequestMapping("/")
	@ResponseBody
	String root() {
		return welcomeMessage;
	}

	@RequestMapping("/couriers")
	@ResponseBody
	List<Courier> courier() {
		return courierRepository.findAll().stream().collect(Collectors.toList());
	}

	@RequestMapping("/orders")
	@ResponseBody
	List<OrderVM> orders() {
		return orderRepository.findAll().stream().map(order -> new OrderVM(order.getId(), order.getDescription()))
				.collect(Collectors.toList());
	}

	@GetMapping("/orders/{courierId}")
	@ResponseBody
	List<OrderVM> orders(@PathVariable(value = "courierId", required = true) String courierId) {
		List<Order> orderList = orderRepository.findAll();

		if (orderList == null || orderList.isEmpty()) {
			throw new OrderNotFoundException(
					"All orders have been delivered, very cool!!!, wait for few seconds and we will send the details.");
		}

		Courier courier = courierRepository.findById(courierId);
		if (courier == null) {
			throw new CourierNotFoundException(
					String.format("No courier person is available with provided courier id:%s ", courierId));
		}
		List<Order> filteredOrder = courierService.filterDataAsPerCriteria(courier, orderList);
		sortService.sortOrder(courier, filteredOrder);
		return orderList.stream().map(order -> new OrderVM(order.getId(), order.getDescription()))
				.collect(Collectors.toList());
	}

	public static void main(String[] args) {
		SpringApplication.run(API.class);
	}
}
