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

import com.hand2heart.project.model.Barang;
import com.hand2heart.project.service.BarangService;

@RestController
@RequestMapping("/api/barang")
@CrossOrigin(origins = "http://localhost:3000")
public class BarangController {

    @Autowired
    private BarangService barangService;

    @PostMapping
    public ResponseEntity<Barang> addBarang(@RequestBody Barang barang) {
    if (barang.getPantiAsuhan() == null || barang.getPantiAsuhan().getId() == null) {
        return ResponseEntity.badRequest().body(null); // Pastikan panti ID tidak null
    }
    Barang savedBarang = barangService.saveBarang(barang);
    return ResponseEntity.ok(savedBarang);
    }


    @GetMapping("/{pantiId}")
    public ResponseEntity<List<Barang>> getBarangByPantiId(@PathVariable Long pantiId) {
        List<Barang> barangList = barangService.getBarangByPantiId(pantiId);
        return ResponseEntity.ok(barangList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBarang(@PathVariable Long id) {
        try {
            barangService.deleteBarang(id);
            return ResponseEntity.ok("Barang deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
