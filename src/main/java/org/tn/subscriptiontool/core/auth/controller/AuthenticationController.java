package org.tn.subscriptiontool.core.auth.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tn.subscriptiontool.core.auth.models.User;

@RestController
@RequestMapping(value = "api/user")
public class AuthenticationController {

    @PostMapping(value = "/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/get/{userName}")
    public ResponseEntity<User> getByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(null);
    }

}
