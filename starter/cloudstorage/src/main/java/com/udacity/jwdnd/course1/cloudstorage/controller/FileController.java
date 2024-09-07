package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping
    public String uploadFile(Authentication auth, MultipartFile fileUpload, Model model) throws IOException {
        String message = null;
        if(fileUpload.isEmpty()){
            message = "Please select file before upload";
            model.addAttribute("errorMessage", message);
            return "result";
        }

        int result = fileService.insertFile(fileUpload, auth.getName());
        if(result > 0){
            message = "File uploaded successfully";
            model.addAttribute("successMessage", message);
        }else{
            message = "File uploaded fail";
            model.addAttribute("errorMessage", message);
        }

        System.out.println(model);

        return "result";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Integer fileId){
        File file = fileService.findFileById(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model){
        int result = fileService.deleteFile(fileId);

        if(result < 0){
            model.addAttribute("errorMessage", "Delete file fail");
            return "result";
        }

        model.addAttribute("successMessage", "Delete file successfully");
        return "result";
    }
}
