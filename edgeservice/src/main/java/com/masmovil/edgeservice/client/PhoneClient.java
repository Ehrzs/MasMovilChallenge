package com.masmovil.edgeservice.client;

import java.util.List;

import com.masmovil.edgeservice.dto.Phone;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "phone-microservice", path="phone")
public interface PhoneClient {

  @GetMapping
  List<Phone> getPhones();

  @GetMapping("/{id}")
  public ResponseEntity<Phone> getPhone(@PathVariable Integer id);

}