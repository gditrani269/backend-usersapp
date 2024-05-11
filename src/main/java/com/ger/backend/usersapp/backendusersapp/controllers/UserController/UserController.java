package com.ger.backend.usersapp.backendusersapp.controllers.UserController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.ger.backend.usersapp.backendusersapp.models.entities.User;
import com.ger.backend.usersapp.backendusersapp.services.UserService;

@RestController
@RequestMapping ("/users")
@CrossOrigin (originPatterns = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list () {
        return service.findAll();
    }

    @GetMapping ("/{id}")  
    public ResponseEntity<?> show (@PathVariable Long id){
        Optional<User> userOptional = service.findById(id);

        if (userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create (@RequestBody User user) {
        System.out.println("PROBANDO el POST");
        System.out.println(user);
        User userDb = service.save (user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDb);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> update (@RequestBody User user, @PathVariable Long id) {
        Optional<User> o = service.update(user, id);
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove (@PathVariable Long id) {
        Optional<User> o = service.findById(id);
        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); //204
        }
        return ResponseEntity.notFound().build();
    }

}
