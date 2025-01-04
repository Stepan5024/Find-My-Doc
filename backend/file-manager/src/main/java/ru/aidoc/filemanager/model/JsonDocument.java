package ru.aidoc.filemanager.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document(collection = "json_documents") // Указываем имя коллекции в MongoDB
public class JsonDocument {
    @Id
    private String id;
    private Map<String, Object> data; // Для хранения любого JSON

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}