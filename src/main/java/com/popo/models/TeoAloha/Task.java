package com.popo.models.TeoAloha;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

// @Data
// @Entity(name = "task")
// @Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    public String title;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    @JsonIgnore
    public Task parentTask;

    @Transient
    public Long parentTaskId;

    @OneToMany(mappedBy = "parentTask")
    public List<Task> childTasks;

    public Task() {
    }

    // Constructors, getters, setters, and other methods

}
