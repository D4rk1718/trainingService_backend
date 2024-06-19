package com.simplavolei.trainingplanservice.models;

import lombok.Data;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class TrainingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Mesocycle> mesocycles = new ArrayList<>(); // Inicialización de la lista

    // Método para agregar un mesociclo al plan de entrenamiento
    public void addMesocycle(Mesocycle mesocycle) {
        mesocycles.add(mesocycle);
        mesocycle.setTrainingPlan(this);
    }

    // Método para eliminar un mesociclo del plan de entrenamiento
    public void removeMesocycle(Mesocycle mesocycle) {
        mesocycles.remove(mesocycle);
        mesocycle.setTrainingPlan(null);
    }
}

