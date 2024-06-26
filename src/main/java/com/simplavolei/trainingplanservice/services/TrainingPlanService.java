package com.simplavolei.trainingplanservice.services;

import com.simplavolei.trainingplanservice.models.Mesocycle;
import com.simplavolei.trainingplanservice.models.Microcycle;
import com.simplavolei.trainingplanservice.models.TrainingPlan;
import com.simplavolei.trainingplanservice.repositories.TrainingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingPlanService {

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    @Autowired
    private MesocycleService mesocycleService;
    
    @Autowired
    private MicrocycleService microcycleService;
    
    
    @Transactional
    public TrainingPlan createTrainingPlan(TrainingPlan trainingPlan) {
        // Verificar si ya existe un plan con el mismo nombre
        Optional<TrainingPlan> existingPlan = trainingPlanRepository.findFirstByName(trainingPlan.getName());
        if (existingPlan.isPresent()) {
            throw new RuntimeException("Ya existe un plan de entrenamiento con el nombre " + trainingPlan.getName());
        }

        // Asignar el plan a cada mesociclo y guardar en la base de datos
        for (Mesocycle mesocycle : trainingPlan.getMesocycles()) {
            mesocycle.setTrainingPlan(trainingPlan);
            // Asignar el plan a cada microciclo dentro del mesociclo
            for (Microcycle microcycle : mesocycle.getMicrocycles()) {
                microcycle.setMesocycle(mesocycle);
            }
        }

        return trainingPlanRepository.save(trainingPlan);
    }

    @Transactional
    public TrainingPlan updateTrainingPlan(Long id, TrainingPlan updatedTrainingPlan) {
        Optional<TrainingPlan> optionalTrainingPlan = trainingPlanRepository.findById(id);
        if (optionalTrainingPlan.isPresent()) {
            TrainingPlan existingTrainingPlan = optionalTrainingPlan.get();
            existingTrainingPlan.setName(updatedTrainingPlan.getName());
            existingTrainingPlan.setDescription(updatedTrainingPlan.getDescription());
            existingTrainingPlan.setFechaInicio(updatedTrainingPlan.getFechaInicio());
            existingTrainingPlan.setFechaFin(updatedTrainingPlan.getFechaFin());

            // Limpiar mesociclos existentes y actualizar con nuevos mesociclos
            existingTrainingPlan.getMesocycles().clear();
            for (Mesocycle mesocycle : updatedTrainingPlan.getMesocycles()) {
                mesocycle.setTrainingPlan(existingTrainingPlan);
                existingTrainingPlan.getMesocycles().add(mesocycle);

                // Asignar el plan a cada microciclo dentro del mesociclo
                for (Microcycle microcycle : mesocycle.getMicrocycles()) {
                    microcycle.setMesocycle(mesocycle);
                }
            }

            return trainingPlanRepository.save(existingTrainingPlan);
        } else {
            throw new RuntimeException("TrainingPlan not found with id " + id);
        }
    }

    @Transactional
    public void deleteTrainingPlan(Long id) {
        trainingPlanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TrainingPlan> getAllTrainingPlans() {
        return trainingPlanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TrainingPlan getTrainingPlanById(Long id) {
        return trainingPlanRepository.findById(id).orElse(null);
    }

    // Delegar la creación o actualización de Mesociclo a MesocycleService
 // Métodos para manejar Mesociclos

    @Transactional
    public Mesocycle createMesocycle(Long planId, Mesocycle mesocycle) {
        Optional<TrainingPlan> optionalTrainingPlan = trainingPlanRepository.findById(planId);
        if (optionalTrainingPlan.isPresent()) {
            TrainingPlan trainingPlan = optionalTrainingPlan.get();
            mesocycle.setTrainingPlan(trainingPlan);
            return mesocycleService.createMesocycle(planId,mesocycle);
        } else {
            throw new RuntimeException("TrainingPlan not found with id " + planId);
        }
    }

    @Transactional(readOnly = true)
    public List<Mesocycle> getMesocyclesForPlan(Long planId) {
        Optional<TrainingPlan> optionalTrainingPlan = trainingPlanRepository.findById(planId);
        if (optionalTrainingPlan.isPresent()) {
            TrainingPlan trainingPlan = optionalTrainingPlan.get();
            return trainingPlan.getMesocycles();
        } else {
            throw new RuntimeException("TrainingPlan not found with id " + planId);
        }
    }
    @Transactional(readOnly = true)
    public Mesocycle getMesocycleById(Long planId, Long mesocycleId) {
        Optional<Mesocycle> optionalMesocycle = mesocycleService.getMesocycleById(mesocycleId);
        if (optionalMesocycle.isPresent()) {
            Mesocycle mesocycle = optionalMesocycle.get();
            // Verificar que el mesociclo pertenezca al plan de entrenamiento
            if (mesocycle.getTrainingPlan().getId().equals(planId)) {
                return mesocycle;
            } else {
                throw new RuntimeException("Mesocycle with id " + mesocycleId + " does not belong to TrainingPlan with id " + planId);
            }
        } else {
            throw new RuntimeException("Mesocycle not found with id " + mesocycleId);
        }
    }


    @Transactional
    public Mesocycle updateMesocycle(Long planId, Long mesocycleId, Mesocycle updatedMesocycle) {
        return mesocycleService.updateMesocycle(planId, mesocycleId, updatedMesocycle);
    }

    @Transactional
    public void deleteMesocycle(Long mesocycleId) {
    	System.out.print("este es el ide que mandaaa  "+mesocycleId);
        mesocycleService.deleteMesocycle(mesocycleId);
    }

    // Métodos para manejar Microciclos

    @Transactional
    public Microcycle createMicrocycle(Long planId, Long mesocycleId, Microcycle microcycle) {
        Optional<Mesocycle> optionalMesocycle = mesocycleService.getMesocycleById(mesocycleId);
        if (optionalMesocycle.isPresent()) {
            Mesocycle mesocycle = optionalMesocycle.get();
            microcycle.setMesocycle(mesocycle);
            return microcycleService.createMicrocycle(microcycle);
        } else {
            throw new RuntimeException("Mesocycle not found with id " + mesocycleId);
        }
    }

    @Transactional(readOnly = true)
    public List<Microcycle> getMicrocyclesForMesocycle(Long planId, Long mesocycleId) {
        Optional<Mesocycle> optionalMesocycle = mesocycleService.getMesocycleById(mesocycleId);
        if (optionalMesocycle.isPresent()) {
            Mesocycle mesocycle = optionalMesocycle.get();
            return mesocycle.getMicrocycles();
        } else {
            throw new RuntimeException("Mesocycle not found with id " + mesocycleId);
        }
    }

    @Transactional(readOnly = true)
    public Microcycle getMicrocycleById(Long planId, Long mesocycleId, Long microcycleId) {
        Optional<Microcycle> optionalMicrocycle = microcycleService.getMicrocycleById(microcycleId);
        if (optionalMicrocycle.isPresent()) {
            Microcycle microcycle = optionalMicrocycle.get();
            // Verificar que el microciclo pertenezca al mesociclo y al plan
            if (microcycle.getMesocycle().getId().equals(mesocycleId) && microcycle.getMesocycle().getTrainingPlan().getId().equals(planId)) {
                return microcycle;
            } else {
                throw new RuntimeException("Microcycle not found with id " + microcycleId + " for mesocycle id " + mesocycleId + " and plan id " + planId);
            }
        } else {
            throw new RuntimeException("Microcycle not found with id " + microcycleId);
        }
    }
    @Transactional
    public Microcycle updateMicrocycle(Long planId, Long mesocycleId, Long microcycleId, Microcycle updatedMicrocycle) {
        return microcycleService.updateMicrocycle(microcycleId, updatedMicrocycle);
    }

    @Transactional
    public void deleteMicrocycle(Long planId, Long mesocycleId, Long microcycleId) {
        microcycleService.deleteMicrocycle(microcycleId);
    }
}