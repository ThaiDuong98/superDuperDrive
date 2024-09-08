package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;

    public  NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping()
    public String saveOrUpdateNote(Authentication auth, Note note, Model model){
        String userName = auth.getName();

        if(note.getNoteId() == null){
            int newNote = noteService.insertNote(userName, note);
            if(newNote < 0){
                model.addAttribute("errorMessage", "Add note fail. Please try again.");
            }else{
                model.addAttribute("successMessage", "Add note successfully.");
            }
        }else{
            int updateNote = noteService.updateNote(userName, note);
            if(updateNote < 0){
                model.addAttribute("errorMessage", "Update note fail. Please try again.");
            }else{
                model.addAttribute("successMessage", "Update note successfully.");
            }
        }

        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model){
        int result = noteService.deleteNote(noteId);

        if(result < 0){
            model.addAttribute("errorMessage", "Delete note fail");
            return "result";
        }

        model.addAttribute("successMessage", "Delete note successfully");
        return "result";
    }
}
