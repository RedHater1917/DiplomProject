package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "EQUIPMENTRECORDS_EQUIPMENT_STATE_CHANGE")
@Entity(name = "equipmentrecords_EquipmentStateChange")
@NamePattern("%s:%s|equipment,state")
public class EquipmentStateChange extends StandardEntity {
    private static final long serialVersionUID = -8969851813831506058L;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EQUIPMENT_ID", nullable = false)
    private Equipment equipment;
    @Column(name = "DATE_OF_CHANGE", nullable = false)
    private LocalDate dateOfChange;
    @Column(name = "STATE", nullable = false)
    private String state;

    public void setState(EquipmentStates state) {
        this.state = state == null ? null : state.getId();
    }

    public EquipmentStates getState() {
        return state == null ? null : EquipmentStates.fromId(state);
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public LocalDate getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDate dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

}