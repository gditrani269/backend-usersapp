package com.ger.backend.usersapp.backendusersapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ger.backend.usersapp.backendusersapp.models.entities.User;

public interface UserRepository 
    extends CrudRepository<User, Long>{

}
