package com.product.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double tax;
    
    private String name;
    private double price;
    private double finalPrice; 

	// Constructors, getters, and setters
    public Product() {}

    public Product(Long id, String name, double price,double tax,double finalPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tax = tax;
        this.finalPrice = finalPrice;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    } 

    public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
}