package com.popo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popo.models.Lieu;

public interface LieuRepository extends JpaRepository<Lieu, Long> {

    Lieu findByLabel(String label);

}
