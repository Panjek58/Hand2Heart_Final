package com.hand2heart.project.service;

import java.util.List;

import com.hand2heart.project.model.Barang;

public interface BarangServiceInterface {
    Barang saveBarang(Barang barang);
    List<Barang> getBarangByPantiId(Long pantiId);
    void deleteBarang(Long id);
}
