package ru.aidoc.apigaterway.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;


import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений для API Gateway.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Обработка ошибок валидации.
     *
     * @param ex Исключение WebExchangeBindException.
     * @return Карта ошибок с полями и сообщениями.
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(WebExchangeBindException ex) {
        log.error("Validation exception occurred: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.warn("Validation error on field '{}': {}", fieldName, errorMessage);
        });
        log.info("Validation errors processed: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Обработка всех остальных исключений.
     *
     * @param ex Исключение.
     * @return Сообщение об ошибке.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Произошла внутренняя ошибка сервера");
    }
}