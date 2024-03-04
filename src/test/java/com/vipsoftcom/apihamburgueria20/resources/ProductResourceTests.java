package com.vipsoftcom.apihamburgueria20.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.services.ProductService;
import com.vipsoftcom.apihamburgueria20.services.excepitions.DatabaseException;
import com.vipsoftcom.apihamburgueria20.services.excepitions.ResourceNotFoundException;
import com.vipsoftcom.apihamburgueria20.tests.Factory;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
//	private Long existsId;
//	private Long nonExistingId;
//	private Long dependentId;
//	private ProductDTO productDTO;
//	private List<ProductDTO> listProductDTO;
//	
//	@BeforeEach
//	void setup() throws Exception {
//		existsId = 1L;
//		nonExistingId = 2L;
//		dependentId = 3L;
//		productDTO = Factory.createProductDTO();
//				
//		listProductDTO = List.of(productDTO);
//		
//		when(service.findAll()).thenReturn(listProductDTO);
//
//		
//		when(service.findById(existsId)).thenReturn(productDTO);
//		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
//		
//		when(service.update(eq(existsId), any())).thenReturn(productDTO);
//		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
//		when(service.updatePatch(eq(existsId), any())).thenReturn(productDTO);
//	    when(service.updatePatch(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
//		
//		doNothing().when(service).delete(existsId);
//		doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
//		doThrow(DatabaseException.class).when(service).delete(dependentId);
//		
//		when(service.insert(any())).thenReturn(productDTO);
//	}
//	
//	@Test
//	public void deleteShouldThrowResourceNotFoundExceptionWhenDoesNotIdExists() throws Exception {
//		ResultActions  result = 
//				mockMvc.perform(delete("/products/{id}", nonExistingId)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isNotFound());
//	}
//	
//	@Test
//	public void deleteShouldDoNothingWhenIdExists() throws Exception {
//		ResultActions  result = 
//				mockMvc.perform(delete("/products/{id}", existsId)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isNoContent());
//	}
//	
//	@Test
//	public void insertShouldReturnCreatedAndProductDTO() throws Exception{
//		
//		String jsonBody = objectMapper.writeValueAsString(productDTO);
//		
//		ResultActions  result = 
//				mockMvc.perform(post("/products")
//						.content(jsonBody)
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isCreated());
//		result.andExpect(jsonPath("$.id").exists());
//		result.andExpect(jsonPath("$.name").exists());
//		result.andExpect(jsonPath("$.category").exists());
//		result.andExpect(jsonPath("$.price").exists());
//		result.andExpect(jsonPath("$.img").exists());
//	}
//	
//	@Test
//	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
//		
//		String jsonBody = objectMapper.writeValueAsString(productDTO);
//		
//		ResultActions  result = 
//				mockMvc.perform(put("/products/{id}", nonExistingId)
//						.content(jsonBody)
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isNotFound());
//	}
//	
//	@Test
//	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
//		
//		String jsonBody = objectMapper.writeValueAsString(productDTO);
//		
//		ResultActions  result = 
//				mockMvc.perform(put("/products/{id}", existsId)
//						.content(jsonBody)
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isOk());
//		result.andExpect(jsonPath("$.id").exists());
//		result.andExpect(jsonPath("$.name").exists());
//		result.andExpect(jsonPath("$.category").exists());
//		result.andExpect(jsonPath("$.price").exists());
//		result.andExpect(jsonPath("$.img").exists());
//	}
//	
//	@Test
//	public void updatePatchShouldReturnProductDTOWhenIdExists() throws Exception {
//		
//		String jsonBody = objectMapper.writeValueAsString(productDTO);
//		
//		ResultActions  result = 
//				mockMvc.perform(patch("/products/{id}", existsId)
//						.content(jsonBody)
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isOk());
//		result.andExpect(jsonPath("$.id").exists());
//		result.andExpect(jsonPath("$.name").exists());
//		result.andExpect(jsonPath("$.category").exists());
//		result.andExpect(jsonPath("$.price").exists());
//		result.andExpect(jsonPath("$.img").exists());
//	}
//	
//	@Test
//	public void findAllShouldReturnListProductDTO() throws Exception {
//		ResultActions  result = 
//				mockMvc.perform(get("/products")
//						.accept(MediaType.APPLICATION_JSON));
//		
//	    result.andExpect(status().isOk())
//        .andExpect(jsonPath("$").isArray())
//        .andExpect(jsonPath("$[0].id").exists())
//        .andExpect(jsonPath("$[0].name").exists())
//        .andExpect(jsonPath("$[0].category").exists())
//        .andExpect(jsonPath("$[0].price").exists())
//        .andExpect(jsonPath("$[0].img").exists());
//	}
//	
//	@Test
//	public void findByIdShouldReturnProductDTOWhenIdExist() throws Exception {
//		ResultActions  result = 
//				mockMvc.perform(get("/products/{id}", existsId)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isOk());
//		result.andExpect(jsonPath("$.id").exists());
//		result.andExpect(jsonPath("$.name").exists());
//		result.andExpect(jsonPath("$.category").exists());
//		result.andExpect(jsonPath("$.price").exists());
//		result.andExpect(jsonPath("$.img").exists());
//	}
//	
//	@Test
//	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
//		ResultActions  result = 
//				mockMvc.perform(get("/products/{id}", nonExistingId)
//						.accept(MediaType.APPLICATION_JSON));
//		
//		result.andExpect(status().isNotFound());
//	}
}
