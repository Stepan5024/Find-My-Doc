package ru.aidoc.filemanager.model;


import com.mongodb.client.gridfs.model.GridFSFile;

import java.util.List;

public class AllDocumentsResponse {
    private List<GridFSFile> files;
    private List<JsonDocument> jsonDocuments;

    // Геттеры и сеттеры
    public List<GridFSFile> getFiles() {
        return files;
    }

    public void setFiles(List<GridFSFile> files) {
        this.files = files;
    }

    public List<JsonDocument> getJsonDocuments() {
        return jsonDocuments;
    }

    public void setJsonDocuments(List<JsonDocument> jsonDocuments) {
        this.jsonDocuments = jsonDocuments;
    }
}