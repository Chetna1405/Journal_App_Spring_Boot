package com.SpringBootLearn.JournalApp.controller;

import com.SpringBootLearn.JournalApp.entity.JournalEntry;
import com.SpringBootLearn.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntityController {

    @Autowired
    private JournalEntryService journalEntryService ;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        return new ResponseEntity<>(journalEntryService.getAllEntries() , HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry , HttpStatus.CREATED);
        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
         Optional<JournalEntry> journalEntry = journalEntryService.getById(myId) ;
         if(journalEntry.isPresent()){
             return new ResponseEntity<>(journalEntry.get() , HttpStatus.OK);
         }
         return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId , @RequestBody JournalEntry updateEntry){
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
            return new ResponseEntity<>(HttpStatus.OK) ;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        journalEntryService.delete(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }
}

//controller -> service -> repository