package ru.aidoc.filemanager.controller;


import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aidoc.filemanager.model.AllDocumentsResponse;
import ru.aidoc.filemanager.model.JsonDocument;
import ru.aidoc.filemanager.service.DocumentService;
import ru.aidoc.filemanager.service.JsonDocumentService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private JsonDocumentService jsonDocumentService;

    // Новый эндпоинт для сохранения JSON
    @PostMapping("/save-json")
    public ResponseEntity<String> saveJson(@RequestBody Map<String, Object> jsonData) {
        String documentId = jsonDocumentService.saveJson(jsonData);
        return ResponseEntity.status(HttpStatus.CREATED).body("JSON saved successfully. Document ID: " + documentId);
    }

    // Загрузка файла
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
        String fileId = documentService.uploadFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded successfully. File ID: " + fileId);
    }

    // Скачивание файла
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String fileId) throws IOException {
        byte[] fileContent = documentService.downloadFile(fileId).readAllBytes();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileId + "\"")
                .body(fileContent);
    }

    // Удаление файла
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<String> deleteDocument(@PathVariable String fileId) {
        documentService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<AllDocumentsResponse> getAllDocuments() {
        AllDocumentsResponse response = new AllDocumentsResponse();

        // Получаем все файлы из GridFS
        List<GridFSFile> files = documentService.getAllFiles();
        response.setFiles(files);

        // Получаем все JSON-документы
        List<JsonDocument> jsonDocuments = jsonDocumentService.getAllJsonDocuments();
        response.setJsonDocuments(jsonDocuments);

        return ResponseEntity.ok(response);
    }
}