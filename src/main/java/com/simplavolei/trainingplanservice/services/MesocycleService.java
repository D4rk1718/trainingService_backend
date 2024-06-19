package com.simplavolei.trainingplanservice.services;


import com.simplavolei.trainingplanservice.models.Mesocycle;

import com.simplavolei.trainingplanservice.repositories.MesocycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MesocycleService {

    @Autowired
    private MesocycleRepository mesocycleRepository;

    @Transactional
    public Mesocycle createMesocycle(Long planId, Mesocycle mesocycle) {
        // Obtener el plan de entrenamiento asociado al mesociclo
        Optional<Mesocycle> existingMesocycle = mesocycleRepository.findByName(mesocycle.getName());
        if (existingMesocycle.isPresent()) {
            throw new RuntimeException("A mesocycle with the name " + mesocycle.getName() + " already exists.");
        }
        
        return mesocycleRepository.save(mesocycle);
    }

    @Transactional(readOnly = true)
    public List<Mesocycle> getMesocyclesForPlan(Long planId) {
        // Obtener todos los mesociclos asociados a un plan de entrenamiento
        return mesocycleRepository.findByTrainingPlanId(planId);
    }

    @Transactional(readOnly = true)
    public Optional<Mesocycle> getMesocycleById(Long mesocycleId) {
        // Obtener un mesociclo por su ID
        return mesocycleRepository.findById(mesocycleId);
    }

    @Transactional
    public Mesocycle updateMesocycle(Long planId, Long mesocycleId, Mesocycle updatedMesocycle) {
        // Actualizar un mesociclo existente
    	
        Optional<Mesocycle> optionalMesocycle = mesocycleRepository.findById(mesocycleId);
        if (optionalMesocycle.isPresent()) {
            Mesocycle existingMesocycle = optionalMesocycle.get();

            // Verificar si el mesociclo pertenece al plan de entrenamiento
            if (!existingMesocycle.getTrainingPlan().getId().equals(planId)) {
                throw new RuntimeException("Mesocycle with id " + mesocycleId + " does not belong to TrainingPlan with id " + planId);
            }

            // Verificar si existe otro mesociclo con el mismo nombre (opcional)
            if (!existingMesocycle.getName().equals(updatedMesocycle.getName())) {
                Optional<Mesocycle> duplicateMesocycle = mesocycleRepository.findByName(updatedMesocycle.getName());
                if (duplicateMesocycle.isPresent()) {
                    throw new RuntimeException("A mesocycle with the name " + updatedMesocycle.getName() + " already exists.");
                }
            }

            // Aplicar las actualizaciones al mesociclo existente
            existingMesocycle.setName(updatedMesocycle.getName());
            existingMesocycle.setDescription(updatedMesocycle.getDescription());
            existingMesocycle.setState(updatedMesocycle.getState());
            existingMesocycle.setType(updatedMesocycle.getType());
            

            // Guardar el mesociclo actualizado
            return mesocycleRepository.save(existingMesocycle);
        } else {
            throw new RuntimeException("Mesocycle not found with id " + mesocycleId);
        }}
    

    @Transactional
    public void deleteMesocycle(Long mesocycleId) {
        // Eliminar un mesociclo por su ID
    	System.out.print("este es el ide que mandaaa en el segundo metodo  "+mesocycleId);
    	mesocycleRepository.deleteById(mesocycleId);
        
    }
    @Transactional(readOnly = true)
    public List<Mesocycle> getAllMesocycles() {
        return mesocycleRepository.findAll();
    }
   
    
    }



