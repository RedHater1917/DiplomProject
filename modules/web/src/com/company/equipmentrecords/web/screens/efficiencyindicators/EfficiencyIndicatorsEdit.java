package com.company.equipmentrecords.web.screens.efficiencyindicators;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.EfficiencyIndicators;

@UiController("equipmentrecords_EfficiencyIndicators.edit")
@UiDescriptor("efficiency-indicators-edit.xml")
@EditedEntityContainer("efficiencyIndicatorsDc")
@LoadDataBeforeShow
public class EfficiencyIndicatorsEdit extends StandardEditor<EfficiencyIndicators> {
}