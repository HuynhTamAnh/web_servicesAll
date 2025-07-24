package com.example.ss7.service;

import com.example.ss7.entity.Worker;
import com.example.ss7.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Optional<Worker> getWorkerById(Long id) {
        return workerRepository.findById(id);
    }

    public Worker addWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    public Worker updateWorker(Long id, Worker worker) {
        Optional<Worker> optional = workerRepository.findById(id);
        if (optional.isPresent()) {
            Worker existingWorker = optional.get();
            existingWorker.setFullname(worker.getFullname());
            existingWorker.setPhone(worker.getPhone());
            existingWorker.setAddress(worker.getAddress());
            existingWorker.setSalary(worker.getSalary());
            return workerRepository.save(existingWorker);
        }
        return null;
    }

    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }
}