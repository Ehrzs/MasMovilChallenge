package com.masmovil.edgeservice.dto;

import lombok.Data;

@Data
public class Phone {
  private Integer id;
  private String image;
  private String name;
  private String description;
  private Double price;

  public Integer getId() {
    return this.id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  
  public String getImage() {
    return this.image;
  }
  public void setImage(String image) {
    this.image = image;
  }
  
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  
  public Double getPrice() {
    return this.price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }
}