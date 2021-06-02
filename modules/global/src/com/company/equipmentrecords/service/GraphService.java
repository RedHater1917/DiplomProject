package com.company.equipmentrecords.service;

import com.company.equipmentrecords.entity.*;


import java.util.List;

public interface GraphService {
    String NAME = "equipmentrecords_GraphService";
    <T,F> List<GraphObject<T,F>> getDataForGraph(GraphQuerySettings settings, Class<T> valueColClass, Class<F> groupColClass);
    List<TimeGraphObject> getDataForTimeGraph(TimeGraphQuerySettings settings);
}