package com.company.equipmentrecords.entity;

import java.io.Serializable;

public class GraphQuerySettings implements Serializable,QuerySettings {
    protected String valueCol;
    protected String groupCol;
    protected boolean valueColOrder;
    protected boolean groupColOrder;
    protected String entity;
    protected String colFunc;
    protected String condition;
    
    public String getValueCol() {
        return valueCol;
    }

    public void setValueCol(String valueCol) {
        this.valueCol = valueCol;
    }

    public String getGroupCol() {
        return groupCol;
    }

    public void setGroupCol(String groupCol) {
        this.groupCol = groupCol;
    }

    public boolean isValueColOrder() {
        return valueColOrder;
    }

    public void setValueColOrder(boolean valueColOrder) {
        this.valueColOrder = valueColOrder;
    }

    public boolean isGroupColOrder() {
        return groupColOrder;
    }

    public void setGroupColOrder(boolean groupColOrder) {
        this.groupColOrder = groupColOrder;
    }

    public String getColFunc() {
        return colFunc;
    }

    public void setColFunc(String colFunc) {
        this.colFunc = colFunc;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
    public String getQuery(){
        StringBuilder queryString = new StringBuilder();
        queryString.append("select ").append(this.groupCol).append(" as groupCol,");
        if (this.colFunc != null) {
            queryString.append(this.colFunc).append("(").append(this.valueCol).append(")")
                    .append(" from EQUIPMENTRECORDS_").append(this.entity)
                    .append(" group by groupCol");
        } else {
            queryString.append(this.getValueCol())
                    .append(" from EQUIPMENTRECORDS_").append(this.entity);
        }
        if (this.isGroupColOrder()) {
            queryString.append(" order by groupCol");
        }else if(this.isValueColOrder()){
            queryString.append(" order by ").append(this.getValueCol());
        }else{
            queryString.append(" order by CREATE_TS");
        }
        return queryString.toString();
    }
}
