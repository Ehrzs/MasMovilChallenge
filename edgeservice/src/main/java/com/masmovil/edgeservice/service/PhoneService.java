package com.masmovil.edgeservice.service;

import java.util.List;

import com.masmovil.edgeservice.client.PhoneClient;
import com.masmovil.edgeservice.dto.Phone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("phone")
class PhoneService {

  private final PhoneClient phoneClient;

  public PhoneService(PhoneClient phoneClient) {
    this.phoneClient = phoneClient;
  }

  @GetMapping("/list")
  public List<Phone> phoneList() {
    return phoneClient.getPhones();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Phone> phone(@PathVariable Integer id) {
    return phoneClient.getPhone(id);
  }
}