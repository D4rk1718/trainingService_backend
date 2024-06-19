package com.simplavolei.trainingplanservice.controllers;

import com.simplavolei.trainingplanservice.models.Mesocycle;
import com.simplavolei.trainingplanservice.services.MesocycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesocycles")
public class MesocycleController {

    @Autowired
    private MesocycleService mesocycleService;

   
  
    @GetMapping
    public ResponseEntity<List<Mesocycle>> getAllMesocycles() {
        List<Mesocycle> mesocycles = mesocycleService.getAllMesocycles();
        return ResponseEntity.ok(mesocycles);
    }

    
}

