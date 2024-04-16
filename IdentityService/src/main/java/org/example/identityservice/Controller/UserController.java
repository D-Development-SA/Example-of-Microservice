package org.example.identityservice.Controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.identityservice.Controller.Exception.ClassException.EmptyFieldException;
import org.example.identityservice.Entity.User;
import org.example.identityservice.Security.Token;
import org.example.identityservice.Service.Contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new EmptyFieldException("user");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/validToken")
    public ResponseEntity<Void> validToken(@CookieValue("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            throw new EmptyFieldException("token");
        }
        Token.isValidToken(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
