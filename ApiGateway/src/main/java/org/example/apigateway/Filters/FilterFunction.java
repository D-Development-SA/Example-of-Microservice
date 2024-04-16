package org.example.apigateway.Filters;

import jakarta.servlet.http.Cookie;
import org.example.apigateway.Service.GateWayClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.function.Function;

public class FilterFunction {
    public static Function<ServerRequest, ServerRequest> authFilter(GateWayClient client){
        return request -> {
            List<Cookie> cookies = request.cookies().get("Authorization");

            if (cookies == null || !client.validToken(cookies.get(0).getValue()).getStatusCode().is2xxSuccessful()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

            return ServerRequest.from(request).build();
        };
    }
}
