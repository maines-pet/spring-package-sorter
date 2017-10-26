package com.manalili.package_manager.model;

public enum CourierCharges {
	SMALL (5,0,200), 
	MEDIUM (10,200,500), 
	LARGE (15,500,1000), 
	XLARGE (20,1000,5000);

	private final float price;
	private final float min;
	private final float max;
	
	CourierCharges(float price, float min, float max){
		this.price = price;
		this.min = min;
		this.max = max;
	}

	public float getPrice() {
		return price;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}
	
}
