package ru.aidoc.filemanager.repository;


import ru.aidoc.filemanager.model.JsonDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JsonDocumentRepository extends MongoRepository<JsonDocument, String> {
}