package com.popo.models.TeoAloha;

import com.geta.dao.annotation.Entity;
import com.geta.dao.annotation.Id;
import com.geta.dao.entity.Relation;
import com.geta.utils.annotation.ExportData;

@Entity(name = "test")
public class Tree extends Relation {

    @Id
    @ExportData
    Long id;

    @ExportData
    String base64;

    @ExportData
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
