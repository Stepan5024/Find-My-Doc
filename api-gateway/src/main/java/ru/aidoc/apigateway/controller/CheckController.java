package ru.aidoc.apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.aidoc.apigateway.dto.CheckRequest;

import jakarta.validation.Valid;

/**
 * Контроллер для запуска проверок документов.
 */
@RestController
@RequestMapping("/checks")
public class CheckController {

    private final WebClient webClient;

    public CheckController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Эндпоинт для запуска проверки документа.
     *
     * @param checkRequest Данные для запуска проверки.
     * @return Результат запуска проверки или ошибка.
     */
    @PostMapping("/start")
    public Mono<ResponseEntity<String>> startCheck(@Valid @RequestBody CheckRequest checkRequest) {
        return webClient.post()
                .uri("http://report-generation-service/checks/start")
                .bodyValue(checkRequest)
                .retrieve()
                .toEntity(String.class);
    }

    /**
     * Эндпоинт для получения отчета по проверке.
     *
     * @param checkId Идентификатор проверки.
     * @return Отчет по проверке или ошибка.
     */
    @GetMapping("/report/{checkId}")
    public Mono<ResponseEntity<String>> getReport(@PathVariable String checkId) {
        return webClient.get()
                .uri("http://report-generation-service/checks/report/{checkId}", checkId)
                .retrieve()
                .toEntity(String.class);
    }
}