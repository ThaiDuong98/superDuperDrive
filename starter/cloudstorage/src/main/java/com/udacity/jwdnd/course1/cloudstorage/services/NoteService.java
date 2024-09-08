package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private UserMapper userMapper;
    private NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper){
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(String userName){
        return Optional.ofNullable(userMapper.getUser(userName)).map(user -> noteMapper.getAllNoteByUserId(user.getUserId())).orElseGet(ArrayList::new);
    }

    public int insertNote(String userName, Note note){
        User user = userMapper.getUser(userName);

        if(user == null){
            return -1;
        }

        note.setUserId(user.getUserId());
        return noteMapper.insertNote(note);
    }

    public int updateNote(String userName, Note note){
        User user = userMapper.getUser(userName);
        if(user == null){
            return -1;
        }

        note.setUserId(user.getUserId());
        return noteMapper.updateNote(note);
    }

    public int deleteNote(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }
}
