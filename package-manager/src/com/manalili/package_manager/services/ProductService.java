package com.manalili.package_manager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manalili.package_manager.dao.ProductDao;
import com.manalili.package_manager.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public List<Product> getAllProducts() {
		return productDao.getAllProducts();
	}
	
	
}
