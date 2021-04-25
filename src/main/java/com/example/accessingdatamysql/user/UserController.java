package com.example.accessingdatamysql.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/demo")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addNewUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone)
    {
        boolean userExist = userRepository.existsByEmail(email);

        if(userExist)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("response", "Email already existed."));

        User n = new User();
        n.setName(name);
        n.setEmail(email);
        n.setPhone(phone);

        userRepository.save(n);
        return ResponseEntity.status(HttpStatus.OK).body(Collections.singletonMap("response", "User created"));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
