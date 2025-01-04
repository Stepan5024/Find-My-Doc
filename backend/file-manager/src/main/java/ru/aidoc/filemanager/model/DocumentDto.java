package ru.aidoc.filemanager.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document // Аннотация для MongoDB
@Getter
@Setter
@NoArgsConstructor // Lombok: генерирует конструктор без аргументов
@AllArgsConstructor // Lombok: генерирует конструктор со всеми аргументами
public class DocumentDto {
    @Id // Аннотация для указания идентификатора документа
    private String id;
    private String name;
    private byte[] content;
}