package com.hand2heart.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hand2heart.project.model.Barang;
import com.hand2heart.project.model.Donation;
import com.hand2heart.project.repository.BarangRepository;
import com.hand2heart.project.repository.DonationRepository;
@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private BarangRepository barangRepository;

    public List<Donation> getDonationsByUserId(Long userId) {
        return donationRepository.findByUserId(userId);
    }

    public void processDonation(Donation donation) {
        if (donation.getUser() == null || donation.getUser().getId() == null) {
            throw new IllegalArgumentException("User tidak valid.");
        }
        if (donation.getPantiAsuhan() == null || donation.getPantiAsuhan().getId() == null) {
            throw new IllegalArgumentException("Panti Asuhan tidak valid.");
        }
        if (donation.getAmount() == null || donation.getAmount() <= 0) {
            throw new IllegalArgumentException("Jumlah donasi harus lebih besar dari 0.");
        }
    
        // Validasi barang di database
        Barang barang = barangRepository.findByPantiAsuhanIdAndNamaBarang(
                donation.getPantiAsuhan().getId(),
                donation.getItem()
        ).orElseThrow(() -> new IllegalArgumentException("Barang tidak ditemukan."));
    
        if (donation.getAmount() > barang.getJumlah()) {
            throw new IllegalArgumentException("Jumlah donasi melebihi kebutuhan.");
        }
    
        // Lanjutkan proses donasi
        barang.setJumlah(barang.getJumlah() - donation.getAmount());
        barangRepository.save(barang);
    
        donation.setDate(LocalDate.now());
        donationRepository.save(donation);
    }
    
}
