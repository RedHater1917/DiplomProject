package com.company.equipmentrecords.service;

import com.company.equipmentrecords.entity.GraphObject;

import java.util.Set;

public interface GraphService {
    String NAME = "equipmentrecords_GraphService";
    <T,F> Set<GraphObject<T,F>> getDataForGraph(String col1, Class<T> col1Class, String col2,Class<F> col2Class);
}