package com.simplavolei.trainingplanservice.repositories;

import com.simplavolei.trainingplanservice.models.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {
    Optional<TrainingPlan> findFirstByName(String name);
}
