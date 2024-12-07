package ru.aidoc.apigaterway.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Controller
@Slf4j
public class WebController {

    @GetMapping("/")
    public String index() {
        log.info("Accessing index page");
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        log.info("Accessing login page");
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register() {
        log.info("Accessing register page");
        return "forward:/register.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        log.info("Accessing dashboard page");
        return "forward:/dashboard.html";
    }

    // Add a method to validate the token (implement according to your authentication logic)
    private boolean isValidToken(String authHeader) {
        // Validate the token and return true if valid, false otherwise
        return true; // Replace with actual validation
    }

   /* @GetMapping("/dashboard")
    public Mono<String> dashboard(ServerWebExchange exchange) {
        log.info("Accessing dashboard page");
        try {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            return Mono.just("forward:/dashboard.html");
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


        } catch (Exception e) {
            log.error("Error during dashboard access: ", e);
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        }
    }
        */
}