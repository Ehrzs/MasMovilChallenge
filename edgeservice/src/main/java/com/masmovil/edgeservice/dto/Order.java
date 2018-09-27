package com.masmovil.edgeservice.dto;

import java.util.List;

public class Order {
  
  private Integer id;
  private String name;
  private String surname;
  private String email;
  private List<Phone> phones;
  
  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public String getSurname() {
    return this.surname;
  }
  public void setSurname(String surname) {
    this.surname = surname;
  }
  
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public List<Phone> getPhones() {
    return phones;
  }
  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }
  
}