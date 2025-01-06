package com.hand2heart.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PantiAsuhan extends abstractdoang {
    @Column(nullable = false)
    private String namaPanti;

    @Column(nullable = false)
    private String kota;

    @OneToMany(mappedBy = "pantiAsuhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Barang> barangList;

    @OneToMany(mappedBy = "pantiAsuhan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Donation> donations;

    // Getters and Setters
    public String getNamaPanti() {
        return namaPanti;
    }

    public void setNamaPanti(String namaPanti) {
        this.namaPanti = namaPanti;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public List<Barang> getBarangList() {
        return barangList;
    }

    public void setBarangList(List<Barang> barangList) {
        this.barangList = barangList;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }
}
