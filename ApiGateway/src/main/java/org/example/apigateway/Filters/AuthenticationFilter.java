package org.example.apigateway.Filters;

import org.example.apigateway.Service.GateWayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import static org.example.apigateway.Filters.FilterFunction.authFilter;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


@Configuration
public class AuthenticationFilter{
    @Autowired
    private GateWayClient client;

    @Bean
    public RouterFunction<ServerResponse> filter(){
        return route("wasteManagerServiceRoute")
                .route(RequestPredicates.path("/api/wasteManager/**"), http("http://localhost:8081"))
                .before(authFilter(client))
                .build();
    }
}
