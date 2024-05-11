package com.popo.models.views;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Entity;

@Immutable
@Entity(name = "red_only")
@Builder
@Data
public class RedOnly {

    @Id
    public Long id;

    @Column
    public String color;

    @Column
    public String imma;

    @Column
    public Double kilometrage;

    public RedOnly() {
    }

    public RedOnly(Long id, String color, String imma, Double kilometrage) {
        this.id = id;
        this.color = color;
        this.imma = imma;
        this.kilometrage = kilometrage;
    }

}
