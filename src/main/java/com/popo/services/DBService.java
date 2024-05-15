package com.popo.services;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DBService {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    public void dropAll() {
        String[] tables = new String[] { "works_in_devis", "works_predefined_by_building_type", "works",
                "works_in_devis_details",
                "payement", "devis_set_details", "devis", "building_type_description", "finition_by_building_type",
                "building_type", "building_finition", "unit", "refresh_token", "lieu" };
        for (int i = 0; i < tables.length; i++) {
            Query query = entityManager.createQuery("DELETE FROM  " + tables[i]);
            // query.setParameter("tab_name", tables[i]);
            query.executeUpdate();
        }
        Query query = entityManager.createQuery("DELETE FROM user d WHERE d.roles = 'USER'");
        query.executeUpdate();

    }

}