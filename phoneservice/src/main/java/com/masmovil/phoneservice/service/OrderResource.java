package com.masmovil.phoneservice.service;

import java.util.List;

import com.masmovil.phoneservice.domain.Order;
import com.masmovil.phoneservice.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderResource {

	private OrderRepository orderRepository;

	@Autowired
	public OrderResource(
		OrderRepository orderRepository
	) {
		this.orderRepository = orderRepository;
	}

  @GetMapping
  public List<Order> orders() {
    return orderRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> orders(@PathVariable Integer id) {
		Order order = orderRepository.findById(id).orElse(null);
		HttpStatus status = order != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(order, status);
	}

  @PostMapping
  public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		if (order.getId() != null) {
			return new ResponseEntity<>(order, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(orderRepository.save(order), HttpStatus.CREATED);
	}

  @PutMapping
  public ResponseEntity<Order> editOrder(@RequestBody Order order) {
		return new ResponseEntity<>(orderRepository.save(order), HttpStatus.ACCEPTED);
	}
	
}