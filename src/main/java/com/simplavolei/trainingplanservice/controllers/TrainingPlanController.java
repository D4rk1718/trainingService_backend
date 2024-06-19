package com.simplavolei.trainingplanservice.controllers;

import com.simplavolei.trainingplanservice.models.Mesocycle;
import com.simplavolei.trainingplanservice.models.Microcycle;
import com.simplavolei.trainingplanservice.models.TrainingPlan;
import com.simplavolei.trainingplanservice.services.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-plans")
public class TrainingPlanController {

    @Autowired
    private TrainingPlanService trainingPlanService;

    @PostMapping
    public ResponseEntity<?> createTrainingPlan(@RequestBody TrainingPlan trainingPlan) {
        try {
            TrainingPlan createdTrainingPlan = trainingPlanService.createTrainingPlan(trainingPlan);
            return ResponseEntity.ok(createdTrainingPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrainingPlan(@PathVariable Long id, @RequestBody TrainingPlan updatedTrainingPlan) {
        try {
            TrainingPlan updatedPlan = trainingPlanService.updateTrainingPlan(id, updatedTrainingPlan);
            return ResponseEntity.ok(updatedPlan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingPlan(@PathVariable Long id) {
        trainingPlanService.deleteTrainingPlan(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<TrainingPlan>> getAllTrainingPlans() {
        List<TrainingPlan> trainingPlans = trainingPlanService.getAllTrainingPlans();
        return ResponseEntity.ok(trainingPlans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingPlan> getTrainingPlanById(@PathVariable Long id) {
        TrainingPlan trainingPlan = trainingPlanService.getTrainingPlanById(id);
        if (trainingPlan != null) {
            return ResponseEntity.ok(trainingPlan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mesocycles endpoints

    @PostMapping("/{planId}/mesocycles")
    public ResponseEntity<?> createOrUpdateMesocycle(@PathVariable Long planId, @RequestBody Mesocycle mesocycle) {
        try {
            Mesocycle createdOrUpdateMesocycle = trainingPlanService.createMesocycle(planId, mesocycle);
            return ResponseEntity.ok(createdOrUpdateMesocycle);
        } catch (RuntimeException e) {
        	
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{planId}/mesocycles")
    public ResponseEntity<List<Mesocycle>> getMesocyclesForPlan(@PathVariable Long planId) {
        List<Mesocycle> mesocycles = trainingPlanService.getMesocyclesForPlan(planId);
        return ResponseEntity.ok(mesocycles);
    }

    @GetMapping("/{planId}/mesocycles/{mesocycleId}")
    public ResponseEntity<Mesocycle> getMesocycleById(@PathVariable Long planId, @PathVariable Long mesocycleId) {
        Mesocycle mesocycle = trainingPlanService.getMesocycleById(planId, mesocycleId);
        if (mesocycle != null) {
            return ResponseEntity.ok(mesocycle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{planId}/mesocycles/{mesocycleId}")
    public ResponseEntity<?> updateMesocycle(@PathVariable Long planId, @PathVariable Long mesocycleId, @RequestBody Mesocycle updatedMesocycle) {
        try {
            Mesocycle updated = trainingPlanService.updateMesocycle(planId, mesocycleId, updatedMesocycle);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{planId}/mesocycles/{mesocycleId}")
    public ResponseEntity<Void> deleteMesocycle( @PathVariable Long mesocycleId) {
        trainingPlanService.deleteMesocycle( mesocycleId);
        return ResponseEntity.ok().build();
    }
    

    // Microcycles endpoints

    @PostMapping("/{planId}/mesocycles/{mesocycleId}/microcycles")
    public ResponseEntity<?> createMicrocycle(@PathVariable Long planId, @PathVariable Long mesocycleId, @RequestBody Microcycle microcycle) {
        try {
            Microcycle createdMicrocycle = trainingPlanService.createMicrocycle(planId, mesocycleId, microcycle);
            return ResponseEntity.ok(createdMicrocycle);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{planId}/mesocycles/{mesocycleId}/microcycles")
    public ResponseEntity<List<Microcycle>> getMicrocyclesForMesocycle(@PathVariable Long planId, @PathVariable Long mesocycleId) {
        List<Microcycle> microcycles = trainingPlanService.getMicrocyclesForMesocycle(planId, mesocycleId);
        return ResponseEntity.ok(microcycles);
    }

    @GetMapping("/{planId}/mesocycles/{mesocycleId}/microcycles/{microcycleId}")
    public ResponseEntity<Microcycle> getMicrocycleById(@PathVariable Long planId, @PathVariable Long mesocycleId, @PathVariable Long microcycleId) {
        Microcycle microcycle = trainingPlanService.getMicrocycleById(planId, mesocycleId, microcycleId);
        if (microcycle != null) {
            return ResponseEntity.ok(microcycle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{planId}/mesocycles/{mesocycleId}/microcycles/{microcycleId}")
    public ResponseEntity<?> updateMicrocycle(@PathVariable Long planId, @PathVariable Long mesocycleId, @PathVariable Long microcycleId, @RequestBody Microcycle updatedMicrocycle) {
        try {
            Microcycle updated = trainingPlanService.updateMicrocycle(planId, mesocycleId, microcycleId, updatedMicrocycle);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{planId}/mesocycles/{mesocycleId}/microcycles/{microcycleId}")
    public ResponseEntity<Void> deleteMicrocycle(@PathVariable Long planId, @PathVariable Long mesocycleId, @PathVariable Long microcycleId) {
    	 System.out.print("entro al metodo ");
        trainingPlanService.deleteMicrocycle(planId, mesocycleId, microcycleId);
        return ResponseEntity.ok().build();
       
    }
}
