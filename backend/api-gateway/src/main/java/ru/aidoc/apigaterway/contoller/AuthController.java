package ru.aidoc.apigaterway.contoller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.aidoc.apigaterway.dto.AuthRequest;
import ru.aidoc.apigaterway.dto.AuthResponse;
import ru.aidoc.apigaterway.dto.RegisterRequest;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${webclient.auth-server-url:http://localhost:8081}")
    private String authServerUrl;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        log.info("Attempting to register user with email: {}", registerRequest.email());

        String registerUrl = authServerUrl + "/auth/register";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            HttpEntity<RegisterRequest> requestEntity = new HttpEntity<>(registerRequest, headers);
            ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(
                    registerUrl, HttpMethod.POST, requestEntity, AuthResponse.class);

            log.info("Successfully registered user: {}", registerRequest.email());
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            log.error("Registration failed for user {}: {}", registerRequest.email(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, "Bearer", registerRequest.email(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("Login attempt for user: {}", authRequest.email());

        String loginUrl = authServerUrl + "/auth/login";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);



            HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);
            ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(
                    loginUrl, HttpMethod.POST, requestEntity, AuthResponse.class);

            log.info("Login successful for user: {}", authRequest.email());
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            log.error("Login failed for user {}: {}", authRequest.email(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Bearer", authRequest.email(), null));
        }
    }
}