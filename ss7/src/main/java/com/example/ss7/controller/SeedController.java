package com.example.ss7.controller;

import com.example.ss7.entity.Seed;
import com.example.ss7.service.SeedService;
import org.hibernate.annotations.processing.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seeds")
public class SeedController {
    @Autowired
    private SeedService seedService;
    @GetMapping
    public ResponseEntity<List<Seed>> getAll(){

        return new ResponseEntity<>(seedService.getAllSeeds(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Seed> add(@RequestBody Seed seed){
        return new ResponseEntity<>(seedService.addSeed(seed), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Seed> updateSeed(@RequestBody Seed seed,@PathVariable long id){
        Seed updatedSeed = seedService.updateSeed(seed,id);
        if(updatedSeed==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(updatedSeed, HttpStatus.OK);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Seed> deleteSeed(@PathVariable long id){
        seedService.deleteSeed(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
