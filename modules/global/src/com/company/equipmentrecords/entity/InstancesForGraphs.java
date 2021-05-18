package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;


import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.UUID;


public enum InstancesForGraphs implements EnumClass<Integer> {

    EFFICIENCY(1){
        @Override
        public String[] columns(){
            return new String[]{"worker", "equipment","producedProduction","defectivePercent","date"};
        }
        @Override
        public Class<?>[] dataTypes(){
            return new Class[]{UUID.class,UUID.class,Integer.class,Integer.class, LocalDate.class};
        }
    };

    private Integer id;

    InstancesForGraphs(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public abstract String[] columns();

    public abstract Class<?>[] dataTypes();

    @Nullable
    public static InstancesForGraphs fromId(Integer id) {
        for (InstancesForGraphs at : InstancesForGraphs.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}