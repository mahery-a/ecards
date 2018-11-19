package com.quarrion.ecards.service;

import com.quarrion.ecards.model.User;
import com.quarrion.ecards.model.Role;
import com.quarrion.ecards.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.validation.Valid;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername( s );
        if ( user.isPresent() ) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", s));
        }
    }

    public User findAccountByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername( username );
        if ( user.isPresent() ) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username[%s] not found", username));
        }
    }

    public User registerUser(User user) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.grantAuthority(Role.ROLE_USER);
            return userRepo.save( user );
    }

    @Transactional // To successfully remove the date @Transactional annotation must be added
    public boolean removeAuthenticatedAccount() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User acct = findAccountByUsername(username);
        Long id = acct.getId();
        userRepo.deleteById(id);
        return !userRepo.findById(id).isPresent();
    }

	public List<User> findAll() {
		return userRepo.findAll();
	}

	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}

	public void deleteById(Long id) {
		userRepo.deleteById(id);
	}

	public User save(@Valid User user) {
		return userRepo.save(user);
	}
}
