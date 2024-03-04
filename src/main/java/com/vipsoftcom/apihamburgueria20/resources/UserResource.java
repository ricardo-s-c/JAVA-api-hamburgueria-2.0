package com.vipsoftcom.apihamburgueria20.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vipsoftcom.apihamburgueria20.dto.AuthenticationDTO;
import com.vipsoftcom.apihamburgueria20.dto.RegisterDTO;
import com.vipsoftcom.apihamburgueria20.dto.UserDTO;
import com.vipsoftcom.apihamburgueria20.dto.UserTokenDTO;
import com.vipsoftcom.apihamburgueria20.services.UserService;

@RestController
@RequestMapping("")
public class UserResource {
	
	@Autowired
    private UserService service;
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<UserTokenDTO> login(@RequestBody AuthenticationDTO dto) {
		UserTokenDTO userTokenDTO = service.login(dto);
		return ResponseEntity.ok().body(userTokenDTO);
	}

	@PostMapping(value = "/users")
	public ResponseEntity register(@RequestBody RegisterDTO dto) {
		service.register(dto);
		return ResponseEntity.ok().build();
	}
}
