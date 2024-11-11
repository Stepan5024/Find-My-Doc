package ru.aidoc.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor()
public class DocumentController {


    private WebClient.Builder webClientBuilder;

    @PostMapping("/upload")
    public Mono<ResponseEntity> uploadDocument(@RequestPart("file") FilePart filePart) {
        // Передача файла в соответствующий сервис
        return webClientBuilder.build()
                .post()
                .uri("http://document-service/documents/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("file", filePart))
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }
}
