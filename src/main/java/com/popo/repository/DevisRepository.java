package com.popo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.popo.models.Devis;

public interface DevisRepository extends JpaRepository<Devis, Long> {

    @Query("SELECT d FROM devis d WHERE d.client.id = :user_id")
    List<Devis> getByIdUser(@Param("user_id") int user_id);

    //
    @Query("SELECT SUM(total_price) price_total,EXTRACT(MONTH FROM creation_date) as time_label FROM devis WHERE EXTRACT(YEAR FROM creation_date) = :year_target GROUP BY  time_label ORDER BY time_label ASC")
    Object[][] filterByMonth(@Param("year_target") Long year_target);

    // @Query("SELECT SUM(total_price) price_total,EXTRACT(YEAR FROM creation_date)
    // as time_label FROM devis GROUP BY time_label ORDER BY time_label ASC")
    // Object[][] filterByYear();

    @Query("SELECT DISTINCT(EXTRACT(YEAR FROM creation_date)) as time_label FROM devis GROUP BY  time_label ORDER BY time_label DESC")
    Object[][] getAllYearPresent();

    Devis findByRefDevis(String ref_devis);

}
