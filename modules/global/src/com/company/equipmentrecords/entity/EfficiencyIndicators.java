package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "EQUIPMENTRECORDS_EFFICIENCY_INDICATORS")
@Entity(name = "equipmentrecords_EfficiencyIndicators")
@NamePattern("%s:%s|equipment,worker")
public class EfficiencyIndicators extends StandardEntity {
    private static final long serialVersionUID = -6629255095285213440L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EQUIPMENT_ID")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "WORKER_ID")
    private Worker worker;

    @Column(name = "PRODUCED_PRODUCTION", nullable = false)
    private Integer producedProduction;

    @Column(name = "DEFECTIVE_PERCENT", nullable = false)
    private Integer defectivePercent;
    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setDefectivePercent(Integer defectivePercent) {
        this.defectivePercent = defectivePercent;
    }

    public Integer getDefectivePercent() {
        return defectivePercent;
    }

    public void setProducedProduction(Integer producedProduction) {
        this.producedProduction = producedProduction;
    }

    public Integer getProducedProduction() {
        return producedProduction;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}