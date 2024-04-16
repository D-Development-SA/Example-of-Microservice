package org.example.identityservice.Security;

import lombok.Data;

import java.util.List;

@Data
public class AuthCredentials {
    private String username;
    private String password;
    private List<String> authorities;
}
