package ru.aidoc.apigateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.dto.UserProfileDTO;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    private final WebClient webClient;

    public UserProfileController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @GetMapping("/profile")
    public Mono<ResponseEntity<UserProfileDTO>> getUserProfile() {
        return webClient.get()
                .uri("http://auth-service/api/user/profile")
                .retrieve()
                .bodyToMono(UserProfileDTO.class)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/profile")
    public Mono<ResponseEntity<UserProfileDTO>> updateUserProfile(@RequestBody UserProfileDTO profileDTO) {
        return webClient.put()
                .uri("http://auth-service/api/user/profile")
                .bodyValue(profileDTO)
                .retrieve()
                .bodyToMono(UserProfileDTO.class)
                .map(ResponseEntity::ok);
    }
}