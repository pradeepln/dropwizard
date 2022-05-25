package com.training.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

	//	{
//	    
//	    "xname": "testPMTool22",
//	    "yprice": 1233,
//	    "qoh": 100
//	}
	
	int id;
	String name;
	float price;
	int qoh;
	
	public Product() {
		
	}

	public Product(String name, float price, int qoh) {
		super();
		this.name = name;
		this.price = price;
		this.qoh = qoh;
	}

	// getAbcd() setAbcd() ---> abcd is a property
	@JsonProperty 
	public int getId() {
		return id;
	}

	@JsonProperty
	public void setId(int id) {
		this.id = id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public float getPrice() {
		return price;
	}
	
	@JsonProperty
	public void setPrice(float price) {
		this.price = price;
	}

	@JsonProperty
	public int getQoh() {
		return qoh;
	}

	@JsonProperty
	public void setQoh(int qoh) {
		this.qoh = qoh;
	}
	
	

}

