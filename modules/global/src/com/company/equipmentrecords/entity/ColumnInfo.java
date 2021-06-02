package com.company.equipmentrecords.entity;

import java.io.Serializable;

public class ColumnInfo implements Serializable {
    private Class<?> columnClass;
    private String queryCol;
    public ColumnInfo(Class<?> columnClass, String queryCol) {
        this.columnClass = columnClass;
        this.queryCol = queryCol;
    }

    public Class<?> getColumnClass() {
        return columnClass;
    }

    public String getQueryCol() {
        return queryCol;
    }

    public void setColumnClass(Class<?> columnClass) {
        this.columnClass = columnClass;
    }

    public void setQueryCol(String queryCol) {
        this.queryCol = queryCol;
    }
}
