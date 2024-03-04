package com.vipsoftcom.apihamburgueria20.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.entities.Product;
import com.vipsoftcom.apihamburgueria20.repositories.ProductRepository;
import com.vipsoftcom.apihamburgueria20.services.excepitions.DatabaseException;
import com.vipsoftcom.apihamburgueria20.services.excepitions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	

	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> optional = repository.findById(id);
		Product entity = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		entity.setName(dto.getName());
		entity.setCategory(dto.getCategory());
		entity.setPrice(dto.getPrice());
		entity.setImg(dto.getImg());
		repository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			entity.setName(dto.getName());
			entity.setCategory(dto.getCategory());
			entity.setPrice(dto.getPrice());
			entity.setImg(dto.getImg());
			repository.save(entity);
			return new ProductDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	@Transactional
	public ProductDTO updatePatch(Long id, Map<String, Object> fields) {
		try {
			Product entity = repository.findById(id).get();
			merge(fields, entity);
			repository.save(entity);
			return new ProductDTO(entity);
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			repository.findById(id).get();
		}
		catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		finally {
			try {
				repository.deleteById(id);
			}catch (DataIntegrityViolationException e) {
				throw new DatabaseException("Integrity vilation");
			}
		}
		
	}
	
	
	private void merge(Map<String, Object> fields, Product entity) {
		ObjectMapper objectMapper = new ObjectMapper();
		Product entityConvert = objectMapper.convertValue(fields, Product.class);
		fields.forEach((propertyName, propertyValue) -> {
			Field field = ReflectionUtils.findField(Product.class, propertyName);
			field.setAccessible(true);
			
			Object newValue = ReflectionUtils.getField(field, entityConvert);
			
			ReflectionUtils.setField(field, entity, newValue);
		});
	}

}
