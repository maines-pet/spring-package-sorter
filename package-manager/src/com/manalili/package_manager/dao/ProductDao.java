package com.manalili.package_manager.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manalili.package_manager.model.Product;

@Transactional
@Repository
public class ProductDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Product> getAllProducts(){
		return getSession().createQuery("from Product").list();
	}


	public Product getProduct(Integer id) {
		return getSession().get(Product.class, id);
		
	}
	
}
