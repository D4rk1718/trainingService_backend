package com.simplavolei.trainingplanservice.controllers;

import com.simplavolei.trainingplanservice.models.Microcycle;
import com.simplavolei.trainingplanservice.services.MicrocycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/microcycles")
public class MicrocycleController {

    @Autowired
    private MicrocycleService microcycleService;

    @PostMapping
    public Microcycle createMicrocycle(@RequestBody Microcycle microcycle) {
        return microcycleService.createMicrocycle(microcycle);
    }

    @PutMapping("/{id}")
    public Microcycle updateMicrocycle(@PathVariable Long id, @RequestBody Microcycle microcycle) {
        return microcycleService.updateMicrocycle(id, microcycle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMicrocycle(@PathVariable Long id) {
        microcycleService.deleteMicrocycle(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Microcycle> getAllMicrocycles() {
        return microcycleService.getAllMicrocycles();
    }

  
}
