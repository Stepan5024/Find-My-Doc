package ru.aidoc.filemanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.aidoc.filemanager.model.DocumentDto;

public interface DocumentRepository extends MongoRepository<DocumentDto, String> {
}