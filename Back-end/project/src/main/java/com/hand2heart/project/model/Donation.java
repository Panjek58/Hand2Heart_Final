package com.hand2heart.project.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Donation extends abstractdoang {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user user;

    @ManyToOne
    @JoinColumn(name = "panti_id", nullable = false)
    @JsonBackReference
    private PantiAsuhan pantiAsuhan;

    @Column(nullable = false)
    private String item;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate date;

    // Getters and Setters
    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }

    public PantiAsuhan getPantiAsuhan() {
        return pantiAsuhan;
    }

    public void setPantiAsuhan(PantiAsuhan pantiAsuhan) {
        this.pantiAsuhan = pantiAsuhan;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
