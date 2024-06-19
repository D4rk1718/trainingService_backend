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
