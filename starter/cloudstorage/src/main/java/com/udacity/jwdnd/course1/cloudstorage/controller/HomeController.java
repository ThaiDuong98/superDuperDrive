package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private NoteService noteService;

    public HomeController(FileService fileService, NoteService noteService){
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String getHomePage(Authentication authentication, Model model){
        List<File> listFile = fileService.getListFileByUser(authentication.getName());
        model.addAttribute("listFile", listFile);

        List<Note> notes = noteService.getAllNotes(authentication.getName());
        model.addAttribute("notes", notes);

        return "home";
    }
}
