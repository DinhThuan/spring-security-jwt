package com.example.spring.security.jwt.controller;

import com.example.spring.security.jwt.entities.DeveloperTutorial;
import com.example.spring.security.jwt.response.ResponseMessage;
import com.example.spring.security.jwt.service.CSVService;
import com.example.spring.security.jwt.utils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import java.util.List;

//@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/v1/")
public class CSVController {
    @Autowired
    public CSVService fileService;

    @PostMapping("csv/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);
                message = "Upload the file successfully: " + file.getOriginalFilename();
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(file.getOriginalFilename())
                        .toUriString();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, fileDownloadUri, new Object(), ""));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message, "", new Object(), ""));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message, "", new Object(), ""));
    }

    @GetMapping("csv/tutorials")
    public ResponseEntity<ResponseMessage> getAllTutorials() {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            List<DeveloperTutorial> tutorials = fileService.getAllTutorials();
            responseMessage.setData(tutorials);
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

//            return new ResponseEntity<>(tutorials, HttpStatus.OK);
            return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("csv/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        InputStreamResource file = new InputStreamResource(fileService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
