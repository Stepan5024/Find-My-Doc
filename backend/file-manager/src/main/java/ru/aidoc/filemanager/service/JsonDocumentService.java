package ru.aidoc.filemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aidoc.filemanager.model.JsonDocument;
import ru.aidoc.filemanager.repository.JsonDocumentRepository;

import java.util.List;
import java.util.Map;

@Service
public class JsonDocumentService {

    @Autowired
    private JsonDocumentRepository jsonDocumentRepository;

    public String saveJson(Map<String, Object> jsonData) {
        JsonDocument jsonDocument = new JsonDocument();
        jsonDocument.setData(jsonData);
        JsonDocument savedDocument = jsonDocumentRepository.save(jsonDocument);
        return savedDocument.getId(); // Возвращаем ID сохраненного документа
    }

    // Получение всех JSON-документов
    public List<JsonDocument> getAllJsonDocuments() {
        return jsonDocumentRepository.findAll();
    }
}
