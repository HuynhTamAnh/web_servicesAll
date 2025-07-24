package com.example.ss7.service;

import com.example.ss7.entity.Seed;
import com.example.ss7.repository.SeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//List<Seed> getAllSeeds()
//Seed getSeedById(Long id)
//Seed addSeed(Seed seed)
//Seed updateSeed(Long id, Seed seed)
//void deleteSeed(Long id)
@Service
public class SeedService {
@Autowired
    private SeedRepository seedRepository;
    public List<Seed> getAllSeeds(){
        return seedRepository.findAll();
    }
    public Optional<Seed> getSeedById(Long id){
        return seedRepository.findById(id);
    }
    public Seed addSeed(Seed request){
        return seedRepository.save(request);
    }
    public Seed updateSeed(Seed request, long id){
        Optional<Seed> optional=  seedRepository.findById(id);
        if(optional.isPresent()){
            Seed seed = optional.get();
            seed.setName(request.getName());
            seed.setQuantity(request.getQuantity());
            return seedRepository.save(seed);
        }
        return null;
    }
    public void deleteSeed(long id){
        seedRepository.deleteById(id);
    }
}
