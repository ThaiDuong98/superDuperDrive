package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;

    public CredentialController(CredentialService credentialService){
        this.credentialService = credentialService;
    }

    @PostMapping()
    public String saveOrUpdateCredential(Authentication auth, Credential credential, Model model){
        if(credential.getCredentialId() == null){
            int newCredential = credentialService.insertCredential(credential, auth.getName());
            if(newCredential < 0){
                model.addAttribute("errorMessage", "Add credential fail. Please try again.");
            }else{
                model.addAttribute("successMessage", "Add credential successfully.");
            }
        }else{
            int updateCredential = credentialService.updateCredential(credential, auth.getName());
            if(updateCredential < 0){
                model.addAttribute("errorMessage", "Update credential fail. Please try again.");
            }else{
                model.addAttribute("successMessage", "Update credential successfully.");
            }
        }

        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model){
        int result = credentialService.deleteCredential(credentialId);

        if(result < 0){
            model.addAttribute("errorMessage", "Delete credential fail");
            return "result";
        }

        model.addAttribute("successMessage", "Delete credential successfully");
        return "result";
    }
}
