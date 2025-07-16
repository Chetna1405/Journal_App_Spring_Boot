package com.SpringBootLearn.JournalApp.controller;

import com.SpringBootLearn.JournalApp.entity.JournalEntry;
import com.SpringBootLearn.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

    @Autowired
    private JournalEntryService journalEntryService ;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAllEntries() ;
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getById(myId).orElse(null) ;
    }

    @PutMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId , @RequestBody JournalEntry updateEntry){
        JournalEntry old = journalEntryService.getById(myId).orElse(null);
        if(old !=null){
            if(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("")){
                old.setTitle(updateEntry.getTitle());
            }else{
                old.setTitle(old.getTitle());
            }
            if(updateEntry.getContent() != null && !updateEntry.getContent().equals("")){
                old.setContent(updateEntry.getContent());
            }else{
                old.setContent(old.getContent());
            }
            journalEntryService.saveEntry(old);
        }
        return old ;
    }

    @DeleteMapping("/id/{myId}")
    public void deleteEntry(@PathVariable ObjectId myId){
        journalEntryService.delete(myId);
    }
}

//controller -> service -> repository