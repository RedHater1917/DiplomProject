package com.company.equipmentrecords.entity;

import java.io.Serializable;

public class GraphObject<T,F> implements Serializable{
    private T cordX;
    private F cordY;

    public GraphObject(T cordX,F cordY){
        this.cordX = cordX;
        this.cordY = cordY;
    }

    public T getCordX() {
        return cordX;
    }

    public void setCordX(T cordX) {
        this.cordX = cordX;
    }

    public F getCordY() {
        return cordY;
    }

    public void setCordY(F cordY) {
        this.cordY = cordY;
    }
}
