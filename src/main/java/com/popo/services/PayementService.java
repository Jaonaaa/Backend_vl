package com.popo.services;

import org.springframework.stereotype.Service;

import com.popo.UserAuth.Models.User;
import com.popo.UserAuth.Repository.UserRepository;
import com.popo.models.Devis;
import com.popo.models.Payement;
import com.popo.repository.DevisRepository;
import com.popo.repository.PayementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayementService {

    private final UserRepository userRepository;
    private final PayementRepository payementRepository;
    private final DevisRepository devisRepository;

    public Payement saveBuild(Payement payement) {
        if (payement.getAmount() <= 0) {
            throw new RuntimeException(
                    "Payement amount must be greater than 0");
        }
        User user = userRepository.findById((long) payement.getId_user()).get();

        Devis devisTarget = devisRepository.findById(payement.getDevis().getId()).get();
        Double totalPayed = getTotalPayement(devisTarget);

        Double totalNow = totalPayed + payement.getAmount();

        if (totalNow > devisTarget.getTotal_price()) {
            throw new RuntimeException(
                    "Payement amount must be less than devis total price");
        }
        payement.setClient(user);

        long count = payementRepository.count();
        payement.setRefPaiement("DE" + count + "533" + count);

        payement = payementRepository.save(payement);

        return payement;
    }

    public Double getTotalPayement(Devis devis) {
        Double total = 0.0;
        for (Payement payement : payementRepository.findAllByDevis(devis)) {
            total += payement.getAmount();
        }
        return total;
    }

}
