package com.hand2heart.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hand2heart.project.model.Barang;

public interface BarangRepository extends JpaRepository<Barang, Long> {
    Optional<Barang> findByPantiAsuhanIdAndNamaBarang(Long pantiId, String namaBarang);

    List<Barang> findByPantiAsuhanId(Long pantiId);
}
