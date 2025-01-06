package com.hand2heart.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand2heart.project.model.PantiAsuhan;
import com.hand2heart.project.repository.PantiAsuhanRepository;

@Service
public class PantiAsuhanService {

    @Autowired
    private PantiAsuhanRepository repository;

    public PantiAsuhan savePanti(PantiAsuhan pantiAsuhan) {
        return repository.save(pantiAsuhan);
    }

    public List<PantiAsuhan> getAllPanti() {
        return repository.findAll();
    }

    public void deletePantiById(Long id) {
        repository.deleteById(id);
    }
}
