package org.example.apigateway.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("identity-service")
public interface GateWayClient {
    @PostMapping("/auth/validToken")
    ResponseEntity<Void> validToken(@CookieValue("Authorization") String token);
}
