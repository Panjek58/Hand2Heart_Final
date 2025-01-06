package com.hand2heart.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand2heart.project.model.Barang;
import com.hand2heart.project.repository.BarangRepository;

@Service
public class BarangService implements BarangServiceInterface {

    @Autowired
    private BarangRepository barangRepository;

    @Override
    public Barang saveBarang(Barang barang) {
        return barangRepository.save(barang);
    }

    @Override
    public List<Barang> getBarangByPantiId(Long pantiId) {
        return barangRepository.findByPantiAsuhanId(pantiId);
    }

    @Override
    public void deleteBarang(Long id) {
        barangRepository.deleteById(id);
    }
}
