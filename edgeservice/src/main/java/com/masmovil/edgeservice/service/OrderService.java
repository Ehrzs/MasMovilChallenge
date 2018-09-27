package com.masmovil.edgeservice.service;

import java.util.List;

import com.masmovil.edgeservice.client.OrderClient;
import com.masmovil.edgeservice.client.PhoneClient;
import com.masmovil.edgeservice.dto.Order;
import com.masmovil.edgeservice.dto.Phone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
class OrderService {

  private final OrderClient orderClient;
  private final PhoneClient phoneClient;

  public OrderService(
      OrderClient orderClient,
      PhoneClient phoneClient) {
    this.orderClient = orderClient;
    this.phoneClient = phoneClient;
  }

  @GetMapping("/list")
  public List<Order> orderList() {
    return orderClient.getOrders();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> order(@PathVariable Integer id) {
    return orderClient.getOrder(id);
  }

  @PostMapping
  public ResponseEntity<Order> addOrder(@RequestBody Order order) {
    if(checkOrder(order)) {
      ResponseEntity<Order> result = orderClient.addOrder(order); 
      Order orderResult = order(result.getBody().getId()).getBody();
      Double total = orderResult.getPhones().stream()
        .mapToDouble(phone -> phone.getPrice())
        .sum();
      System.out.println("Total price of order: " + total);
      return result;
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

  private Boolean checkOrder(Order order) {
    for(Phone phone: order.getPhones()) {
      Phone result = phoneClient.getPhone(phone.getId()).getBody();
      HttpStatus status = phoneClient.getPhone(phone.getId()).getStatusCode();
      if(status != HttpStatus.OK) {
        return false;
      }
    };
		return true;
	}
}