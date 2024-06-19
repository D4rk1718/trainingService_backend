package com.simplavolei.trainingplanservice.repositories;

import com.simplavolei.trainingplanservice.models.Microcycle;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MicrocycleRepository extends JpaRepository<Microcycle, Long> {
	Optional<Microcycle> findByName(String name);
}