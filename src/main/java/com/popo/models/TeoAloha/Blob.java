package com.popo.models.TeoAloha;
// package com.geta.caching.model;

// import java.sql.Timestamp;
// import java.time.Instant;

// import com.fasterxml.jackson.annotation.JsonFormat;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity(name = "blob")
// @Table(name = "blob")
// public class Blob {

// @Id
// @GeneratedValue(strategy = GenerationType.AUTO)
// Long id;

// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm",
// timezone = "Europe/Moscow")
// Timestamp time;

// String time_;

// public Blob() {
// }

// public Blob(Timestamp time) {
// this.time = time;
// }

// public Timestamp getTime() {
// return time;
// }

// public String getTime_() {
// return time_;
// }

// public void setTime_(String time_) {
// this.time_ = time_;
// Timestamp timestamp = Timestamp.from(Instant.parse(this.time_));
// System.out.println("Ato am time string " + time_ + " => " +
// timestamp.toString());

// this.time = timestamp;
// }

// public void setTime(Timestamp time) {
// System.out.println("Ato am time " + time.toString());
// this.time = time;
// }
// }
