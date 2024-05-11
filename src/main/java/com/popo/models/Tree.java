package com.popo.models;

import com.geta.dao.annotation.Entity;
import com.geta.dao.annotation.Id;
import com.geta.dao.entity.Relation;

@Entity(name = "test")
public class Tree extends Relation {

    @Id
    Long id;
    String base64;
    String name;

    public Tree() {
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
