package com.ger.backend.usersapp.backendusersapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ger.backend.usersapp.backendusersapp.models.entities.Role;
import com.ger.backend.usersapp.backendusersapp.models.entities.User;
import com.ger.backend.usersapp.backendusersapp.models.request.UserRequest;
import com.ger.backend.usersapp.backendusersapp.repositories.RoleRepository;
import com.ger.backend.usersapp.backendusersapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional (readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        System.out.println("En el save");
        String passwordBCrypt = passwordEncoder.encode (user.getPassword());
        user.setPassword(passwordBCrypt);
        Optional<Role> o = roleRepository.findByname("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        if(o.isPresent()) {
            roles.add (o.orElseThrow());
        }
        user.setRoles(roles);

        return repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        Optional<User> o = this.findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow ();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userOptional = this.save(userDb);
        }
        return Optional.ofNullable(userOptional);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }


}
