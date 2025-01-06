package com.hand2heart.project.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Barang extends abstractdoang {
    @Column(nullable = false)
    private String namaBarang;

    @Column(nullable = false)
    private Integer jumlah;

    @ManyToOne
    @JoinColumn(name = "panti_id", nullable = false)
    @JsonIgnoreProperties("barangList")
    private PantiAsuhan pantiAsuhan;

    // Getters and Setters
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public PantiAsuhan getPantiAsuhan() {
        return pantiAsuhan;
    }

    public void setPantiAsuhan(PantiAsuhan pantiAsuhan) {
        this.pantiAsuhan = pantiAsuhan;
    }
}
