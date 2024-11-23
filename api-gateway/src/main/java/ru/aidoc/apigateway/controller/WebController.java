package ru.aidoc.apigateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.utils.JwtUtil;


@Controller
@Slf4j
public class WebController {

    private final JwtUtil jwtUtil;

    public WebController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String index() {
        log.info("Accessing index page");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        log.info("Accessing login page");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        log.info("Accessing register page");
        return "register";
    }

    @GetMapping("/dashboard")
    public Mono<String> dashboard(ServerWebExchange exchange) {
        log.info("Accessing dashboard page");
        try {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            return Mono.just("dashboard");
            /*if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                log.info("Authorization header found with Bearer token");
                if (jwtUtil.validateToken(token)) {
                    log.info("Token validated successfully");

                } else {
                    log.warn("Token validation failed");
                }
            } else {
                log.warn("Missing or invalid Authorization header");
            }
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

             */
        } catch (Exception e) {
            log.error("Error during dashboard access: ", e);
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        }
    }
}