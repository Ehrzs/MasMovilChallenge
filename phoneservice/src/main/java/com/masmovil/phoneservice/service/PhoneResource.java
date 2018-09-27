package com.masmovil.phoneservice.service;

import java.util.List;

import com.masmovil.phoneservice.domain.Phone;
import com.masmovil.phoneservice.repository.PhoneRepository;

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
@RequestMapping("phone")
public class PhoneResource {

	private PhoneRepository phoneRepository;

	@Autowired
	public PhoneResource(
		PhoneRepository phoneRepository
	) {
		this.phoneRepository = phoneRepository;
	}

  @GetMapping
  public List<Phone> phones() {
    return phoneRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Phone> phones(@PathVariable Integer id) {
		Phone phone = phoneRepository.findById(id).orElse(null);
		HttpStatus status = phone != null ? HttpStatus.OK : HttpStatus.NO_CONTENT;
		return new ResponseEntity<>(phone, status);
	}

  @PostMapping
  public ResponseEntity<Phone> addPhone(@RequestBody Phone phone) {
		if (phone.getId() != null) {
			return new ResponseEntity<>(phone, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(phoneRepository.save(phone), HttpStatus.CREATED);
	}

  @PutMapping
  public ResponseEntity<Phone> editPhone(@RequestBody Phone phone) {
		return new ResponseEntity<>(phoneRepository.save(phone), HttpStatus.ACCEPTED);
	}
	
}