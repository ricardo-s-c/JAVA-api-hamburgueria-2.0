package com.vipsoftcom.apihamburgueria20.tests;

import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L,"HamburguerTESTE", "SanduíchesTESTE", 19.0, "https://i.imgur.com/Vng6VzV.pngTESTE");
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}

}
