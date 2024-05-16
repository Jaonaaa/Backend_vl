package com.popo.repository;

import com.popo.models.Devis;
import com.popo.models.Payement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayementRepository extends JpaRepository<Payement, Long> {

    public List<Payement> findAllByDevis(Devis devis);

    public Optional<Payement> findByRefPaiement(String refPaiement);

}
