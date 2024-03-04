package com.vipsoftcom.apihamburgueria20.dto;

import java.io.Serializable;

import com.vipsoftcom.apihamburgueria20.entities.Product;


public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String name;
    private String category;
    private Double price;
    private String img;
    
    public ProductDTO() {
    	
    }

	public ProductDTO(Long id, String name, String category, Double price, String img) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.img = img;
	}
	
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.category = product.getCategory();
		this.price = product.getPrice();
		this.img = product.getImg();
	}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

    
}
