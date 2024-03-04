package com.vipsoftcom.apihamburgueria20.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vipsoftcom.apihamburgueria20.config.TokenService;
import com.vipsoftcom.apihamburgueria20.dto.AuthenticationDTO;
import com.vipsoftcom.apihamburgueria20.dto.RegisterDTO;
import com.vipsoftcom.apihamburgueria20.dto.UserDTO;
import com.vipsoftcom.apihamburgueria20.dto.UserTokenDTO;
import com.vipsoftcom.apihamburgueria20.entities.User;
import com.vipsoftcom.apihamburgueria20.repositories.UserRepository;
import com.vipsoftcom.apihamburgueria20.services.excepitions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	public UserTokenDTO login(AuthenticationDTO dto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		String token = tokenService.generateToken((User)auth.getPrincipal());
		User user = (User)auth.getPrincipal();
		return new UserTokenDTO(token , new UserDTO(user));
	}
	
	public void register(RegisterDTO dto) {
		
		if(repository.findByEmail(dto.email()) != null) {throw new ResourceNotFoundException("Email já cadastrado ou inválido");} else {
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
		User newUser = new User(dto.name(), dto.email(), encryptedPassword);
		
		repository.save(newUser);
		}
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}
}
