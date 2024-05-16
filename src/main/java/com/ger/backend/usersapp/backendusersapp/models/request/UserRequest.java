package com.ger.backend.usersapp.backendusersapp.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserRequest {


    @NotBlank  //NotEmpty es para validar string, para validar un numero en cambio por ej se usa NotNull, etc
    @Size (min=4, max=8)
    private String username;

    @NotEmpty
    @Email  //valida que tenga formato de email
    private String email;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    
}
