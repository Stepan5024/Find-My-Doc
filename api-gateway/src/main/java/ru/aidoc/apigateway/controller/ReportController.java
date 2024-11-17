package ru.aidoc.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.dto.CheckRequest;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportController {


    private WebClient.Builder webClientBuilder;

    @GetMapping("/{reportId}")
    public Mono<ResponseEntity> getReport(@PathVariable String reportId) {
        return webClientBuilder.build()
                .get()
                .uri("http://report-generation-service/reports/" + reportId)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }

    @PostMapping("/start")
    public Mono<ResponseEntity> startCheck(@RequestBody CheckRequest checkRequest) {
        return webClientBuilder.build()
                .post()
                .uri("http://report-generation-service/reports/start")
                .body(Mono.just(checkRequest), CheckRequest.class)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }
}
