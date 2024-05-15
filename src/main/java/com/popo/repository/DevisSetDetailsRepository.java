package com.popo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.popo.models.Devis;
import com.popo.models.DevisSetDetails;

public interface DevisSetDetailsRepository extends JpaRepository<DevisSetDetails, Long> {

    List<DevisSetDetails> findByDevis(Devis devis);

    List<DevisSetDetails> findByDevisAndParentDevisSetDetailsIsNull(Devis devis);

}
