package com.SpringBootLearn.JournalApp.service;

import com.SpringBootLearn.JournalApp.entity.JournalEntry;
import com.SpringBootLearn.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    //business logic to interact with database

    @Autowired
    private JournalEntryRepository journalEntryRepository ; //Dependency injection

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry) ;
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void delete(ObjectId id){
        journalEntryRepository.deleteById(id);
    }


}
