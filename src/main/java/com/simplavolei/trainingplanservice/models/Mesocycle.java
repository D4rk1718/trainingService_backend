package com.simplavolei.trainingplanservice.models;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Mesocycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("name")
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "training_plan_id")
    private TrainingPlan trainingPlan;
 // Enumeración para el estado del Mesociclo
   
    @Enumerated(EnumType.STRING)
    private MesocycleType type;
    @Enumerated(EnumType.STRING)
    private MesocycleState state;

    @OneToMany(mappedBy = "mesocycle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Microcycle> microcycles = new ArrayList<>(); ;

    // Method to add a microcycle to the mesocycle
    public void addMicrocycle(Microcycle microcycle) {
        microcycles.add(microcycle);
        microcycle.setMesocycle(this);
    }

    // Method to remove a microcycle from the mesocycle
    public void removeMicrocycle(Microcycle microcycle) {
        microcycles.remove(microcycle);
        microcycle.setMesocycle(null);
    }
    public enum MesocycleState {
        PLANNED,
        ACTIVE,
        COMPLETED,
        CANCELED
    }
    public enum MesocycleType {
        FUERZA,
        RESISTENCIA
       
    }
}

