package com.masmovil.phoneservice.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "orders")
public class Order {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NonNull
  @Size(max = 100)
  private String name;

  @NonNull
  @Size(max = 100)
  private String surname;

  @NonNull
  @Size(max = 100)
  @Email
  private String email;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "order_phones",
    joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "phone_id", referencedColumnName = "id")
  )
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