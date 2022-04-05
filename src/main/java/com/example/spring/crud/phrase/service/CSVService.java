package com.example.spring.crud.phrase.service;

import com.example.spring.crud.phrase.entities.DeveloperTutorial;
import com.example.spring.crud.phrase.repository.DeveloperTutorialRepository;
import com.example.spring.crud.phrase.utils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    DeveloperTutorialRepository repository;

    public void save(MultipartFile file) {
        try {
            List<DeveloperTutorial> tutorials = CSVHelper.csvTutorials(file.getInputStream(), file.getOriginalFilename());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<DeveloperTutorial> tutorials = repository.findAll();
        ByteArrayInputStream in = CSVHelper.tutorialToCSV(tutorials);
        return in;
    }

    public List<DeveloperTutorial> getAllTutorials() {
        return repository.findAll();
    }
}
