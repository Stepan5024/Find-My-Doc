package ru.aidoc.filemanager.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    // Загрузка файла в GridFS
    public String uploadFile(MultipartFile file) throws IOException {
        // Сохраняем файл в GridFS и возвращаем его идентификатор
        return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString();
    }

    // Скачивание файла из GridFS
    public InputStream downloadFile(String fileId) throws IOException {
        // Находим файл по его идентификатору
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileId)));
        if (gridFSFile == null) {
            throw new RuntimeException("File not found");
        }
        // Возвращаем поток для скачивания
        return gridFsTemplate.getResource(gridFSFile).getInputStream();
    }

    // Удаление файла из GridFS
    public void deleteFile(String fileId) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(fileId)));
    }

    // Получение списка всех файлов
    public List<GridFSFile> getAllFiles() {
        List<GridFSFile> files = new ArrayList<>();
        gridFsTemplate.find(new Query()).into(files);
        return files;
    }
}