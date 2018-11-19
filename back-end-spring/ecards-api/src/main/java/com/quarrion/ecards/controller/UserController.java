package com.quarrion.ecards.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.quarrion.ecards.exception.UserNotFoundException;
import com.quarrion.ecards.model.User;
import com.quarrion.ecards.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
    UserService userService;

    @PreAuthorize("hasRole('REGISTER')")
    @PostMapping("/api/user/register")
    public ResponseEntity<User> registerAccount(@RequestBody User user) {
        user = userService.registerUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PreAuthorize("isFullyAuthenticated()")
    @DeleteMapping("/api/user/remove")
    public ResponseEntity<GeneralController.RestMsg> removeAccount(Principal principal){
        boolean isDeleted = userService.removeAuthenticatedAccount();
        if ( isDeleted ) {
            return new ResponseEntity<>(
                    new GeneralController.RestMsg(String.format("[%s] removed.", principal.getName())),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<GeneralController.RestMsg>(
                    new GeneralController.RestMsg(String.format("An error ocurred while delete [%s]", principal.getName())),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

	@GetMapping("/user")
	public List<User> retrieveAllUsers() {
		//TODO: https://www.thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/ 
		return service.findAll();
	}

	@GetMapping("/user/{id}")
	public User retrieveUser(@PathVariable Long id) {
		//TODO: https://www.thoughts-on-java.org/5-ways-to-initialize-lazy-relations-and-when-to-use-them/ 
		Optional<User> user = service.findById(id);
		
		if(!user.isPresent())
			throw new UserNotFoundException("id-"+ id);
		
		return user.get(); // TODO: remove this line and re-enable below
		
		// TODO: RE-ENABLE HATEOAS
		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		/*Resource<User> resource = new Resource<User>(user.get());
		
		//HATEOAS link to "all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		ControllerLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		
		
		return resource;*/
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Long id) {
		service.deleteById(id);	
	}

	//
	// input - details of user
	// output - CREATED & Return the created URI
	
	//HATEOAS
	
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		// CREATED
		// /users/{id}     savedUser.getId()
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
}
