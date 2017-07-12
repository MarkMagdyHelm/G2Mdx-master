package com.example.dev_2.g2mdx.Retrofit.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

	@SerializedName("id")
	@Expose
	private Object id;
	@SerializedName("productDescription")
	@Expose
	private String productDescription;
	@SerializedName("image")
	@Expose
	private Image image;
	@SerializedName("price")
	@Expose
	private Integer price;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}