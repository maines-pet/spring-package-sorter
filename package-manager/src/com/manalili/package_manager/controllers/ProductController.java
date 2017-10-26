package com.manalili.package_manager.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manalili.package_manager.model.Product;
import com.manalili.package_manager.model.ShoppingCart;
import com.manalili.package_manager.services.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCart cart;
	
	@RequestMapping("/")
	public String getHome(Model model, @RequestParam(value = "id", required=false) Integer id){
		model.addAttribute("products", productService.getAllProducts());
		return "home"; 
	}
	
	@ModelAttribute("shoppingCart")
	public ShoppingCart getShoppingCart(){
		return cart;
	}
	
	@RequestMapping(value="home")
	public String addToCart(Model model){
		model.addAttribute("products", productService.getAllProducts());
		return "home"; 
	}
	
	@RequestMapping(value="/api/cart", produces="application/json", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCart(@RequestBody Product product){
		Map<String, Object> data = new HashMap<String, Object>();
		cart.addItem(product.getId());
		data.put("cart", cart.getPackages());
		return data;
	}
	
	@RequestMapping(value="/packages", method=RequestMethod.POST)
	public String sendResultFragment(Model model, @RequestBody Product product){
		cart.addItem(product.getId());
		model.addAttribute("cart", cart.getPackages());
		return "result-fragment :: cart-package-result(packages=${cart})";
	}
	
	@RequestMapping(value="/packages/reset", method=RequestMethod.GET)
	public String sendEmptyPackage(){
		cart.removeEverything();
		return "result-fragment :: empty-shopping-cart";
	}
	
	
}
