package com.masmovil.edgeservice.client;

import java.util.List;

import com.masmovil.edgeservice.dto.Phone;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("phone-microservice")
public interface PhoneClient {

  @GetMapping("phone")
  List<Phone> getPhones();

}