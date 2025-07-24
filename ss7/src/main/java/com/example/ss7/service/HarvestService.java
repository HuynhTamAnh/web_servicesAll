package com.example.ss7.service;

import com.example.ss7.entity.Harvest;
import com.example.ss7.repository.HarvestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HarvestService {

    @Autowired
    private HarvestRepository harvestRepository;

    public List<Harvest> getAllHarvests() {
        return harvestRepository.findAll();
    }

    public Optional<Harvest> getHarvestById(Long id) {
        return harvestRepository.findById(id);
    }

    public Harvest addHarvest(Harvest harvest) {
        return harvestRepository.save(harvest);
    }
}
