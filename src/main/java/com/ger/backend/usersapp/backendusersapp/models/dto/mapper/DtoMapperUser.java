package com.ger.backend.usersapp.backendusersapp.models.dto.mapper;

import javax.management.RuntimeErrorException;

import com.ger.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.ger.backend.usersapp.backendusersapp.models.entities.User;

public class DtoMapperUser {

    //static DtoMapperUser mapper esto podria traer conflicto por ser una variable estatica cuando haya mas usuario, porque al ser estatica se usa en toda la aplcacion.
    //private static DtoMapperUser mapper;

    private User user;
    private DtoMapperUser () {

    }

    public static DtoMapperUser builder () {
        //aca tambien cambiamos, simplemente devolvemos la instancia para salvar el problema del static de 
        //static DtoMapperUser mapper, asi cada instancia es unica por cada usuario
        return new DtoMapperUser ();
    }

    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDto build () {
        if (user == null) {
            throw new RuntimeException("Debe pasar el entity user!");
        }
        boolean isAdmin = user.getRoles().stream().anyMatch( r -> "ROLE_ADMIN".equals(r.getName()));
        return new UserDto (this.user.getId(), user.getUsername(), user.getEmail(), isAdmin);
    }
    

}
