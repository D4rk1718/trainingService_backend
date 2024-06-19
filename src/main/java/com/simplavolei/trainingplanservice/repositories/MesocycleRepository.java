package com.simplavolei.trainingplanservice.repositories;

import com.simplavolei.trainingplanservice.models.Mesocycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MesocycleRepository extends JpaRepository<Mesocycle, Long> {

    // Método para encontrar todos los mesociclos por el ID del plan de entrenamiento
    List<Mesocycle> findByTrainingPlanId(Long trainingPlanId);
    Optional<Mesocycle> findByName(String name);

    
}
