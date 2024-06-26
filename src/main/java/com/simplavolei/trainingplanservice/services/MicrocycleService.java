package com.simplavolei.trainingplanservice.services;


import com.simplavolei.trainingplanservice.models.Microcycle;
import com.simplavolei.trainingplanservice.repositories.MicrocycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

@Service
public class MicrocycleService {

    @Autowired
    private MicrocycleRepository microcycleRepository;
    @PersistenceContext
    private EntityManager entityManager;
    

    @Transactional
    public Microcycle createMicrocycle(Microcycle microcycle) {
        Optional<Microcycle> existingMicrocycle = microcycleRepository.findByName(microcycle.getName());
        if (existingMicrocycle.isPresent()) {
            throw new RuntimeException("A microcycle with the name " + microcycle.getName() + " already exists.");
        }
        return microcycleRepository.save(microcycle);
    }

    @Transactional
    public Microcycle updateMicrocycle(Long microcycleId, Microcycle updatedMicrocycle) {
        Optional<Microcycle> optionalMicrocycle = microcycleRepository.findById(microcycleId);
        if (optionalMicrocycle.isPresent()) {
            Microcycle existingMicrocycle = optionalMicrocycle.get();
            if (!existingMicrocycle.getName().equals(updatedMicrocycle.getName())) {
                Optional<Microcycle> duplicateMicrocycle = microcycleRepository.findByName(updatedMicrocycle.getName());
                if (duplicateMicrocycle.isPresent()) {
                    throw new RuntimeException("A microcycle with the name " + updatedMicrocycle.getName() + " already exists.");
                }
            }
            existingMicrocycle.setName(updatedMicrocycle.getName());
            existingMicrocycle.setDescription(updatedMicrocycle.getDescription());
            existingMicrocycle.setState(updatedMicrocycle.getState());
            existingMicrocycle.setCantidadDeRepeticiones(updatedMicrocycle.getCantidadDeRepeticiones());
            existingMicrocycle.setCantidadDeSeries(updatedMicrocycle.getCantidadDeSeries());
            existingMicrocycle.setTiempoDeDescanso(updatedMicrocycle.getTiempoDeDescanso());
            // Add additional updates as needed
            return microcycleRepository.save(existingMicrocycle);
        } else {
            throw new RuntimeException("Microcycle not found with id " + microcycleId);
        }
    }

 
    @Transactional
    public void deleteMicrocycle(Long id) {
        try {
            microcycleRepository.deleteById(id);
            System.out.println("Microcycle with id " + id + " successfully deleted.");
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            throw new RuntimeException("Error deleting Microcycle with id: " + id, e);
        }
    }
    

    @Transactional(readOnly = true)
    public List<Microcycle> getAllMicrocycles() {
        return microcycleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Microcycle> getMicrocycleById(Long id) {
        return microcycleRepository.findById(id);
    }
}
