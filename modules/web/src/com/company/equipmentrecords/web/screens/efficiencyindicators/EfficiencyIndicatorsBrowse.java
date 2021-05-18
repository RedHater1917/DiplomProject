package com.company.equipmentrecords.web.screens.efficiencyindicators;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.EfficiencyIndicators;

@UiController("equipmentrecords_EfficiencyIndicators.browse")
@UiDescriptor("efficiency-indicators-browse.xml")
@LookupComponent("efficiencyIndicatorsesTable")
@LoadDataBeforeShow
public class EfficiencyIndicatorsBrowse extends StandardLookup<EfficiencyIndicators> {
}