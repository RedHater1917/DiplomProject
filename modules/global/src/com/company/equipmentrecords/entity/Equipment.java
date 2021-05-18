package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Table(name = "EQUIPMENTRECORDS_EQUIPMENT")
@Entity(name = "equipmentrecords_Equipment")
@NamePattern("%s:%s|number,name")
public class Equipment extends StandardEntity {
    private static final long serialVersionUID = -7722761921059989251L;
    @GeneratedValue
    @Column(name = "NUMBER", nullable = false, unique = true)
    private Long number;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MADE_BY", nullable = false)
    private String madeBy;
    @Column(name = "COST", nullable = false)
    private Integer cost;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

}