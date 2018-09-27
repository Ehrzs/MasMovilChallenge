package com.masmovil.edgeservice.service;

import java.util.List;

import com.masmovil.edgeservice.client.PhoneClient;
import com.masmovil.edgeservice.dto.Phone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PhoneService {

  private final PhoneClient phoneClient;

  public PhoneService(PhoneClient phoneClient) {
    this.phoneClient = phoneClient;
  }

  @GetMapping("/phone-list")
  public List<Phone> phoneList() {
    return phoneClient.getPhones();
  }
}