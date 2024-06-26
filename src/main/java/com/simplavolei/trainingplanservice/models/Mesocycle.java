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
    
    @Enumerated(EnumType.STRING)
    private MesocycleType type;

    @Enumerated(EnumType.STRING)
    private MesocycleState state;

    private String periodoDeEntrenamiento; // Nuevo campo para el periodo de entrenamiento

    @Column(length = 1000) // Aumentar la longitud si es necesario
    private String objetivosEspecificos; // Nuevo campo para los objetivos específicos

    @Column(length = 1000) // Aumentar la longitud si es necesario
    private String objetivosGenerales; // Nuevo campo para los objetivos generales

    @ManyToOne
    @JoinColumn(name = "training_plan_id")
    private TrainingPlan trainingPlan;

   

    @OneToMany(mappedBy = "mesocycle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Microcycle> microcycles = new ArrayList<>();

    // Método para agregar un microciclo al mesociclo
    public void addMicrocycle(Microcycle microcycle) {
        microcycles.add(microcycle);
        microcycle.setMesocycle(this);
    }

    // Método para eliminar un microciclo del mesociclo
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

