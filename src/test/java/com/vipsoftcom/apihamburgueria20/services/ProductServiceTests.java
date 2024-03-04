package com.vipsoftcom.apihamburgueria20.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.entities.Product;
import com.vipsoftcom.apihamburgueria20.repositories.ProductRepository;
import com.vipsoftcom.apihamburgueria20.services.excepitions.DatabaseException;
import com.vipsoftcom.apihamburgueria20.services.excepitions.ResourceNotFoundException;
import com.vipsoftcom.apihamburgueria20.tests.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private Product product;
	private ProductDTO productDTO;
	private List<Product> productList;
	private long existingId;
	private long nonExistingId;
	private Long dependentId;
	private long countTotalProducts;
	
	@BeforeEach
	void setup() throws Exception {
		existingId    = 1L;
		nonExistingId = 1000L;
		dependentId = 3L;
		countTotalProducts = 6L;
		product = Factory.createProduct();
		productList = List.of(product);
		productDTO = new ProductDTO(product);
		
		Mockito.when(repository.findAll()).thenReturn(productList);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.doThrow(ResourceNotFoundException.class).when(repository).findById(nonExistingId);
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doNothing().when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptiontWhenDoesNotIdExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDTO);
		});
		Mockito.verify(repository, Mockito.times(1)).getReferenceById(nonExistingId);
	}
	
	@Test
	public void updateShouldReturnProducDTOtWhenIdExists() {
		
		ProductDTO updateDTO = service.update(existingId, productDTO);
		
		
		Assertions.assertNotNull(updateDTO);
		Mockito.verify(repository, Mockito.times(1)).getReferenceById(existingId);
		Mockito.verify(repository, Mockito.times(1)).save(product);
	}
	
	@Test
	public void updatePatchShouldReturnProducDTOtWhenIdExists() {
		Map<String, Object> fields = new HashMap<>();
		fields.put("name", "Novo Nome");
		fields.put("price", 29.99);
		
		ProductDTO updateDTO = service.updatePatch(existingId, fields);
		
		Assertions.assertNotNull(updateDTO);
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
		Mockito.verify(repository, Mockito.times(1)).save(product);
	}
	
	@Test
	public void insertShouldSaveAndReturnProductDTO() {
		
		ProductDTO resultDTO = service.insert(productDTO);
		product.setId(resultDTO.getId());
		
		Assertions.assertNotNull(resultDTO);
		Mockito.verify(repository, Mockito.times(1)).save(product);
	}
	
	   @Test
	    public void findAllShouldReturnProductList() {

	        List<ProductDTO> result = service.findAll();

	        Assertions.assertNotNull(result);
	        Mockito.verify(repository, Mockito.times(1)).findAll();
	    }
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenDoesNotIdExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});
		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
	}
	
	@Test
	public void findByIdShouldReturnProducDTOtWhenIdExists() {
		
		ProductDTO findByIdProductDTO = service.findById(existingId);
		
		Assertions.assertNotNull(findByIdProductDTO);
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
	}
	
	@Test
	public void deleteShouldThrowDataIntegrityViolationExceptionWhenDependentIdExists() {
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findById(dependentId);
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldDoNohingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdNotexists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
}
