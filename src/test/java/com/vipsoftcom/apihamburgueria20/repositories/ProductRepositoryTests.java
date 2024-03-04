package com.vipsoftcom.apihamburgueria20.repositories;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vipsoftcom.apihamburgueria20.entities.Product;
import com.vipsoftcom.apihamburgueria20.tests.Factory;


@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setup() throws Exception {
		existingId    = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 6L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Product product = Factory.createProduct();
		product.setId(null);
		
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	@Test
	public void findByIdShouldReturnOptionalWhenIdExist() {
		Optional<Product> result = repository.findById(existingId);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdNotExist() {
		Optional<Product> result = repository.findById(nonExistingId);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(existingId);
		
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowNoSuchElementExceptionWhenIdNotexists() {
		Product product = repository.findById(nonExistingId).orElse(null);
		
		Assertions.assertNull(product);
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			repository.findById(nonExistingId).get();
		});
	}
}
