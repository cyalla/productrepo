package com.product.dto;

public class ProductDTO {

    private Long id;
    private String name;
    private String price;
    private String tax;
    private String finalPrice; 


	// Constructors, getters, and setters
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String price,String tax,String finalPrice) {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }  
	
    public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
	
	
}
