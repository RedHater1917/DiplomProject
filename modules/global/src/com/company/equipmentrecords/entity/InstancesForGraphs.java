package com.company.equipmentrecords.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;
import jdk.vm.ci.meta.Local;


import javax.annotation.Nullable;
import javax.persistence.Column;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public enum InstancesForGraphs implements EnumClass<Integer> {

    EFFICIENCY_INDICATORS(1){
        @Override
        public Map<String,ColumnInfo> columnInfoMap() {
            return new HashMap<String, ColumnInfo>(){
                {
                    put("Worker name", new ColumnInfo(String.class,
                            "(SELECT FIO FROM EQUIPMENTRECORDS_WORKER WHERE ID = WORKER_ID)"));
                    put("Equipment name", new ColumnInfo(String.class,
                            "(SELECT NAME FROM EQUIPMENTRECORDS_EQUIPMENT WHERE ID = EQUIPMENT_ID)"));
                    put("Produced production", new ColumnInfo(Integer.class,
                            "PRODUCED_PRODUCTION"));
                    put("Defective percent", new ColumnInfo(Double.class,
                            "DEFECTIVE_PERCENT"));
                    put("Date",new ColumnInfo(LocalDate.class,
                            "DATE"));
                }
            };
        }
    },
    EQUIPMENT(2){
        @Override
        public Map<String, ColumnInfo> columnInfoMap() {
            return new HashMap<String, ColumnInfo>(){
                {
                    put("Name", new ColumnInfo(String.class,
                            "NAME"));
                    put("Made by", new ColumnInfo(String.class,
                            "MADE_BY"));
                    put("Cost",new ColumnInfo(Integer.class,
                            "COST"));
                }
            };
        }
    },
    WORKER(3){
        @Override
        public Map<String, ColumnInfo> columnInfoMap() {
            return new HashMap<String, ColumnInfo>() {
                {
                    put("Full name", new ColumnInfo(String.class,
                            "FIO"));
                    put("Age", new ColumnInfo(Integer.class,
                            "AGE"));
                    put("Experience", new ColumnInfo(Integer.class,
                            "EXPERIENCE"));
                    put("Position", new ColumnInfo(String.class,
                            "POSITION_"));
                }
            };
        }
    };

    private Integer id;

    InstancesForGraphs(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public abstract Map<String,ColumnInfo>  columnInfoMap();

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