// package com.popo.repository.views;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

// import com.popo.models.views.RedOnly;

// public interface RedOnlyRepository extends JpaRepository<RedOnly, Long> {

// @Query("SELECT r FROM red_only r WHERE r.kilometrage < 1000")
// List<?> alaivo();
// }
