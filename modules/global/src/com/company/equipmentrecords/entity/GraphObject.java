package com.company.equipmentrecords.entity;

import java.io.Serializable;

public class GraphObject<T,F> implements Serializable{
    private T value;
    private F category;

    public GraphObject(T value,F category){
        this.value = value;
        this.category = category;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public F getCategory() {
        return category;
    }

    public void setCategory(F category) {
        this.category = category;
    }
}
