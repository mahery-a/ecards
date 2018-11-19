package com.quarrion.ecards.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.quarrion.ecards.exception.UserNotFoundException;
import com.quarrion.ecards.model.Ecard;
import com.quarrion.ecards.model.User;
import com.quarrion.ecards.repository.EcardRepository;
import com.quarrion.ecards.repository.UserRepository;

@RestController
public class UserJPAController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EcardRepository ecardRepository;

	@GetMapping("/jpa/user")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/user/{id}")
	public Resource<User> retrieveUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);

		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
		Resource<User> resource = new Resource<User>(user.get());

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(linkTo.withRel("all-users"));

		// HATEOAS

		return resource;
	}

	@DeleteMapping("/jpa/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}

	//
	// input - details of user
	// output - CREATED & Return the created URI

	// HATEOAS

	@PostMapping("/jpa/user")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/jpa/user/{id}/ecards")
	public List<Ecard> retrieveAllUsers(@PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		return userOptional.get().getEcards();
	}


	@PostMapping("/jpa/user/{id}/ecards")
	public ResponseEntity<Object> createEcard(@PathVariable Long id, @RequestBody Ecard ecard) {
		
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		User user = userOptional.get();
		
		ecard.setUser(user);
		
		ecardRepository.save(ecard);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ecard.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

}
