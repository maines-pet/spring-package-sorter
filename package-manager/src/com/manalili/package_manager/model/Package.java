package com.manalili.package_manager.model;

import java.util.ArrayList;
import java.util.List;

public class Package {
	public static final float MAX_PACKAGE_AMOUNT = 250f;
	public static final int WILL_EXCEED = 1;
	
	private List<Product> products = new ArrayList<>();
	private float maxWeight;
	private float price;

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addItem(Product product) throws ExceedingMaximumException{
		if (getTotalWeight() + product.getWeight() > maxWeight){
			String errMessage = String.format("Exceeded maximum weight for product %s weight %f. Max weight for package is %f", 
					product.getName(), product.getWeight(), maxWeight);;
			throw new ExceedingMaximumException(errMessage);
		} else if (getTotalPrice() + product.getPrice() > MAX_PACKAGE_AMOUNT){
			String errMessage = String.format("Exceeded maximum price for product %s price %f. Total price is %f before adding. Max package amount is %f.", 
					product.getName(), product.getPrice(), getTotalPrice(), MAX_PACKAGE_AMOUNT);;
			throw new ExceedingMaximumException(errMessage);
		} else {
			products.add(product);
		}
	}
	
	public float getTotalPrice() {
		return (float) products.stream().mapToDouble(e -> e.getPrice()).sum();
	}

	public float getTotalWeight() {
		return (float) products.stream().mapToDouble(e -> e.getWeight()).sum();
	}
	
	@Override
	public String toString() {
		return "Package [products=" + products + "]";
	}

	public float getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	class ExceedingMaximumException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ExceedingMaximumException(String message){
			super(message);
		}
	}
	
	
}
