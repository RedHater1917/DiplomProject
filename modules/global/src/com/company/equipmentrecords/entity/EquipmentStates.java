package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum EquipmentStates implements EnumClass<String> {

    NORMAL("normal"), BROKEN("broken"),SOLD("sold");

    private final String id;

    EquipmentStates(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static EquipmentStates fromId(String id) {
        for (EquipmentStates at : EquipmentStates.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}