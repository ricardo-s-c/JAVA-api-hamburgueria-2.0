package com.vipsoftcom.apihamburgueria20.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existsId;
	private Long nonExistingId;
	private Long countFinalProduct;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setup() throws Exception {
		existsId = 1L;
		nonExistingId = 2000L;
		countFinalProduct = 6L;
		productDTO = Factory.createProductDTO();
	}
	
	@Test
	public void findAllPageShouldReturnSortedPageWhenSortedByName() throws Exception {
		
		ResultActions  result = 
				mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countFinalProduct));
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].name").value("Big Kenzie"));
		result.andExpect(jsonPath("$.content[1].name").value("Coca-Cola"));
		result.andExpect(jsonPath("$.content[2].name").value("Fanta Guaraná"));
		result.andExpect(jsonPath("$.content[3].name").value("Hamburguer"));
	}
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		String expectedName = productDTO.getName();
		String expecteCategory = productDTO.getCategory();
		String expectePrice = productDTO.getPrice().toString();
		String expecteImg = productDTO.getImg();
		
		ResultActions  result = 
				mockMvc.perform(put("/products/{id}", existsId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existsId));
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.category").value(expecteCategory));
		result.andExpect(jsonPath("$.price").value(expectePrice));
		result.andExpect(jsonPath("$.img").value(expecteImg));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(productDTO);
		
		ResultActions  result = 
				mockMvc.perform(put("/products/{id}", nonExistingId)
						.content(jsonBody)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
}
