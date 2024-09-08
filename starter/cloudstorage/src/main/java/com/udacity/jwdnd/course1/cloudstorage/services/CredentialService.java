package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;

    public  CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public Credential decryptPassword(Credential credential){
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }

    public List<Credential> getAllCredentials(String userName){
        return Optional.ofNullable(userMapper.getUser(userName))
                .map(user -> credentialMapper.getAllCredentials(user.getUserId()))
                .orElseGet(ArrayList::new)
                .stream()
                .map(this::decryptPassword)
                .collect(Collectors.toList());
    }

    public int insertCredential(Credential credential, String userName){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedKey = Base64.getEncoder().encodeToString(salt);

        User user = userMapper.getUser(userName);
        String password = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        if(user == null){
            return -1;
        }
        System.out.println("du" + credential);

        credential.setPassword(password);
        credential.setUserId(user.getUserId());
        credential.setKey(encodedKey);

        return credentialMapper.insertCredential(credential);
    }

    public int updateCredential(Credential credential, String userName){
        Credential exsistCredential = credentialMapper.getCredentialById(credential.getCredentialId());

        credential.setKey(exsistCredential.getKey());
        credential.setUserId(userMapper.getUser(userName).getUserId());
        credential.setPassword(exsistCredential.getPassword());

        return credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }
}
