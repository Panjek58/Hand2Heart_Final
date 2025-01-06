package com.hand2heart.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hand2heart.project.model.PantiAsuhan;
import com.hand2heart.project.service.PantiAsuhanService;

@RestController
@RequestMapping("/api/panti")
@CrossOrigin(origins = "http://localhost:3000")
public class PantiAsuhanController {

    @Autowired
    private PantiAsuhanService service;

    @PostMapping
    public ResponseEntity<PantiAsuhan> addPanti(@RequestBody PantiAsuhan pantiAsuhan) {
        PantiAsuhan savedPanti = service.savePanti(pantiAsuhan);
        return ResponseEntity.ok(savedPanti);
    }

    @GetMapping
    public ResponseEntity<List<PantiAsuhan>> getAllPanti() {
        List<PantiAsuhan> pantiList = service.getAllPanti();
        return ResponseEntity.ok(pantiList);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePanti(@PathVariable Long id) {
        try {
            service.deletePantiById(id);
            return ResponseEntity.ok("Panti Asuhan deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting Panti Asuhan");
        }
    }
}
