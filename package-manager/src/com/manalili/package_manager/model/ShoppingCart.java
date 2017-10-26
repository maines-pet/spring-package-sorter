package com.manalili.package_manager.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.manalili.package_manager.dao.ProductDao;
import com.manalili.package_manager.model.Package.ExceedingMaximumException;

public class ShoppingCart {

	private List<Package> packages = new ArrayList<>();
	private List<Product> items = new ArrayList<>();

	private float totalPrice = 0f;
	private float totalWeight = 0f;
	private static final float MAX_PRODUCT_PRICE_PER_PACKAGE = 250.0f;

	private static final Comparator<Product> PRODUCT_ORDER = (p0, p1) -> 
		p0.getWeight() < p1.getWeight() ? 1 : 
			(p0.getWeight() == p1.getWeight() ? (p0.getPrice() < p1.getPrice() ? 1 : -1)
					: -1);
	
	private static final Comparator<Package> ASCENDING_PACKAGE_ORDER = (p0, p1) -> 
		p0.getTotalWeight() < p1.getTotalWeight() ? -1 : 
			(p0.getTotalWeight() == p1.getTotalWeight() ? (p0.getTotalPrice() < p1.getTotalPrice() ? -1 : 1)
					: 1);

	private List <CourierCharges> charges = new ArrayList<>(Arrays.asList(CourierCharges.values()));

	@Autowired
	private ProductDao productDao;

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public void addItem(Integer id) {
		if (id == null) {
			return;
		}
		Product prod = productDao.getProduct(id);
		items.add(prod);
		arrangeItemsInPackage();
	}
	
	public void removeEverything(){
		packages.clear();
		items.clear();
		totalPrice = 0f;
		totalWeight = 0f;
	}

	private void arrangeItemsInPackage() {
		
		if (packages != null) {
			packages.clear();
		};
		
		setTotalPrice(items.stream().map(e -> e.getPrice())
				.reduce(0f, Float::sum));
		setTotalWeight(items.stream().map(e -> e.getWeight())
				.reduce(0f, Float::sum));
		items.sort(PRODUCT_ORDER);
		int numOfPackages = (int) Math.ceil(totalPrice / MAX_PRODUCT_PRICE_PER_PACKAGE);
		
		//average weight is computed as the requirement ask for evenly distributed package according
		//to weight while maintaining the $250 total price maximum for each package
		float averageWeightAcrossPackages = totalWeight / (float) numOfPackages;

		CourierCharges charge = pickCourierType(averageWeightAcrossPackages);
		
		for (int i = 0; i < items.size(); i++) {
			if (i < numOfPackages) {
				addInitialPackagesToList(items.get(i), charge, true);
				continue;
			}
			packages.sort(ASCENDING_PACKAGE_ORDER);
			Boolean itemAdded = false;
			for (int j = 0; j < packages.size(); j++) {
				try {
					packages.get(j).addItem(items.get(i));
					itemAdded = true;
					break; //move on to the next item to be added
				} catch (ExceedingMaximumException e1) {
					//Ignore exception
					//Try to add the product to the next package
				}
			}
			
			//For items that can't be included with the initial packages created,
			//create a new package for it
			if (!itemAdded){
				float weightOfTheCurrentItem = items.get(i).getWeight();
				CourierCharges getCharge = pickCourierType(weightOfTheCurrentItem); 
				addInitialPackagesToList(items.get(i), getCharge, false);
			}
		}	
		
	}

	//boolean var "recurse" is used to check if calling method wants to use recursion feature
	//recursion feature is only used when the product to be added in the package will not fit based on
	//the maximum average weight that package can hold
	private void addInitialPackagesToList(Product product, CourierCharges charge, boolean recurse){
		Package packageToBeAdded = new Package();
		try {
			packageToBeAdded.setMaxWeight(charge.getMax());
			packageToBeAdded.setPrice(charge.getPrice());
			packageToBeAdded.addItem(product);
			packages.add(packageToBeAdded);
		} catch (ExceedingMaximumException e1) {
			if (recurse){
				addInitialPackagesToList(product, pickCourierType(product.getWeight()), false);
			} else {
				e1.printStackTrace();
			}
			
		}
	}
	
	private CourierCharges pickCourierType(float weight){
		return charges.stream()
		       	.filter(o -> (o.getMin() < weight) 
			    		   && (weight <= o.getMax()))
			    	.findFirst().get();
	}
	
	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public float getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Override
	public String toString() {
		return "ShoppingCart [packages=" + packages + ", items=" + items
				+ ", totalPrice=" + totalPrice + ", totalWeight=" + totalWeight
				+ ", productDao=" + productDao + "]";
	}
}
