package com.ger.backend.usersapp.backendusersapp.controllers.UserController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ger.backend.usersapp.backendusersapp.models.dto.UserDto;
import com.ger.backend.usersapp.backendusersapp.models.entities.User;
import com.ger.backend.usersapp.backendusersapp.models.request.UserRequest;
import com.ger.backend.usersapp.backendusersapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/users")
@CrossOrigin (originPatterns = "*") //origins para configurar un dominio que no sea local
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDto> list () {
        return service.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<UserDto> list (@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        return service.findAll(pageable);
    }

    @GetMapping ("/{id}")  
    public ResponseEntity<?> show (@PathVariable Long id){
        Optional<UserDto> userOptional = service.findById(id);

        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create (@Valid @RequestBody User user, BindingResult result) {
        System.out.println("PROBANDO el POST");
        System.out.println(user);
        System.out.println("result: " + result);
        if (result.hasErrors()) {
            return validation (result);
        }
     //   User userDb = service.save (user);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save (user));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation (result);
        }
        Optional<UserDto> o = service.update(user, id);
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove (@PathVariable Long id) {
        Optional<UserDto> o = service.findById(id);
        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        System.out.println("Metodo: validation");
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
