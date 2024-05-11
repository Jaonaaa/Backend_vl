package com.popo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.popo.models.Car;

public interface VaikaRepository extends JpaRepository<Car, Long> {

    List<Car> findByColor(String color);

    @Query("SELECT ca FROM car ca WHERE color LIKE %:color_%")
    List<Car> findByColorLike(@Param("color_") String color);

    // @Modifying if update or delete

    // @Query("SELECT ca FROM red_only ca")
    // List<Car> findAllRed();
}
