package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "EQUIPMENTRECORDS_WORKER")
@Entity(name = "equipmentrecords_Worker")
@NamePattern("%s|fio")
public class Worker extends StandardEntity {
    private static final long serialVersionUID = 2787805215837504600L;
    @Column(name = "FIO")
    private String fio;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "EXPERIENCE")
    private Integer experience;
    @Column(name = "POSITION_")
    private String position;//Может быть enum

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFio() {
        return fio;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

}