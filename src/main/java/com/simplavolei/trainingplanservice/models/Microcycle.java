package com.simplavolei.trainingplanservice.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Microcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    
    private int cantidadDeSeries; // Nuevo campo para la cantidad de series
    private int cantidadDeRepeticiones; // Nuevo campo para la cantidad de repeticiones
    private int tiempoDeDescanso; // Nuevo campo para el tiempo de descanso en segundos

    @Enumerated(EnumType.STRING)
    private MicrocycleState state;

    @ManyToOne
    @JoinColumn(name = "mesocycle_id")
    private Mesocycle mesocycle;
    
    public enum MicrocycleState {
        PLANNED,
        ACTIVE,
        COMPLETED,
        CANCELED
    }
}
