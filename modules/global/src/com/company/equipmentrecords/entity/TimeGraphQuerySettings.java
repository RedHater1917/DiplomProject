package com.company.equipmentrecords.entity;

import java.io.Serializable;

public class TimeGraphQuerySettings implements Serializable,QuerySettings {
    private String col1Query;
    private String col2Query;
    private String dateCol;
    private String entity;
    private String dateCondition;

    public String getCol1Query() {
        return col1Query;
    }

    public void setCol1Query(String col1Query) {
        this.col1Query = col1Query;
    }

    public String getCol2Query() {
        return col2Query;
    }

    public void setCol2Query(String col2Query) {
        this.col2Query = col2Query;
    }

    public String getDateCol() {
        return dateCol;
    }

    public void setDateCol(String dateCol) {
        this.dateCol = dateCol;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDateCondition() {
        return dateCondition;
    }

    public void setDateCondition(String dateCondition) {
        this.dateCondition = dateCondition;
    }

    @Override
    public String getQuery() {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select ").append(this.dateCol).append(",").append(this.col1Query).append(",")
                .append(this.col2Query).append("from EQUIPMENTRECORDS_").append(this.entity)
                .append(this.dateCondition);
        return queryString.toString();
    }
}
