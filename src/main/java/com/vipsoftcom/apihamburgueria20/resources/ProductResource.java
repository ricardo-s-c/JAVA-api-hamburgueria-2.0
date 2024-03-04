package com.vipsoftcom.apihamburgueria20.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vipsoftcom.apihamburgueria20.resources.exceptions.StandardError;
import com.vipsoftcom.apihamburgueria20.dto.ProductDTO;
import com.vipsoftcom.apihamburgueria20.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Api(tags = "Product Controller", value = "ProductController", description = "Controller for Product")
@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService service;

	
    @GetMapping
    @ApiOperation(value = "List of Products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products listed with successfully"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
	public ResponseEntity<List<ProductDTO>> findAll() {
		List<ProductDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		ProductDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
    @PostMapping
//    @Operation(summary = "Create a new Product", security = {@SecurityRequirement(name = "bearer-key")})
//    @ApiOperation(value = "Create a new Product")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "201", description = "Product created successfully",
//            content = { @Content(mediaType = "application/json",
//                schema = @Schema(implementation = ProductDTO.class)) }),
//        @ApiResponse(responseCode = "400", description = "Bad Request")
//    })
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> updatePatch(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
		ProductDTO dto = service.updatePatch(id, fields);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

