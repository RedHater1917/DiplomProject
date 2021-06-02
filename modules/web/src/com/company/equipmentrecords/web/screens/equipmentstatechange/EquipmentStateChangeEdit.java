package com.company.equipmentrecords.web.screens.equipmentstatechange;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.EquipmentStateChange;

@UiController("equipmentrecords_EquipmentStateChange.edit")
@UiDescriptor("equipment-state-change-edit.xml")
@EditedEntityContainer("equipmentStateChangeDc")
@LoadDataBeforeShow
public class EquipmentStateChangeEdit extends StandardEditor<EquipmentStateChange> {
}