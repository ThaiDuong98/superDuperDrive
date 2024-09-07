package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private UserMapper userMapper;
    private FileMapper fileMapper;

    public FileService(UserMapper userMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    public List<File> getListFileByUser(String username) {
        return Optional.ofNullable(userMapper.getUser(username)).map(user -> fileMapper.getAllFileByUserId(user.getUserId())).orElseGet(ArrayList::new);
    }

    public File findFileById(Integer fileId){
        return fileMapper.findFileById(fileId);
    }

    public int insertFile(MultipartFile multipartFile, String userName) throws IOException {
        File file = new File();
        User user = userMapper.getUser(userName);

        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(Long.toString(multipartFile.getSize()));
        file.setFileData(multipartFile.getBytes());

        return fileMapper.insertFile(file, user.getUserId());
    }

    public int deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }
}
