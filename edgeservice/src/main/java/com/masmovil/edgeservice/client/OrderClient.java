package com.masmovil.edgeservice.client;

import java.util.List;

import com.masmovil.edgeservice.dto.Order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "phone-microservice", path = "order")
public interface OrderClient {

  @GetMapping
  List<Order> getOrders();

  @GetMapping("/{id}")
  ResponseEntity<Order> getOrder(@PathVariable Integer id);

  @PostMapping
  public ResponseEntity<Order> addOrder(@RequestBody Order order);

}