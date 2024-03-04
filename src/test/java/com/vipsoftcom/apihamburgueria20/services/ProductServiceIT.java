package com.vipsoftcom.apihamburgueria20.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.repositories.ProductRepository;
import com.vipsoftcom.apihamburgueria20.services.excepitions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class ProductServiceIT {

	@Autowired
	private ProductService service;
	
	@Autowired
	private ProductRepository repository;
	
	private Long existsId;
	private Long nonExistingId;
	private Long countFinalProduct;
	
	@BeforeEach
	void setup() throws Exception {
		existsId = 1L;
		nonExistingId = 2000L;
		countFinalProduct = 6L;
	}
	
    @Test
    public void findAllShouldReturnSortedProductsWhenSortedByName() {
        List<ProductDTO> result = service.findAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Big Kenzie", result.get(0).getName());
        Assertions.assertEquals("Coca-Cola", result.get(1).getName());
        Assertions.assertEquals("Fanta Guaraná", result.get(2).getName());
        Assertions.assertEquals("Hamburguer", result.get(3).getName());
    }
    
        @Test
        public void findAllShouldReturnEmptyListWhenNoProductsExist() {
          
            repository.deleteAll();

            List<ProductDTO> result = service.findAll();

            Assertions.assertTrue(result.isEmpty());
        }
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenDoesNotIdExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		service.delete(existsId);
		
		Assertions.assertEquals(countFinalProduct -1, repository.count());
	}
}
