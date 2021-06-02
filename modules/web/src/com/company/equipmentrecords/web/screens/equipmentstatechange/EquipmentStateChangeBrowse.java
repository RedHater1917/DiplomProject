package com.company.equipmentrecords.web.screens.equipmentstatechange;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.EquipmentStateChange;

@UiController("equipmentrecords_EquipmentStateChange.browse")
@UiDescriptor("equipment-state-change-browse.xml")
@LookupComponent("equipmentStateChangesTable")
@LoadDataBeforeShow
public class EquipmentStateChangeBrowse extends StandardLookup<EquipmentStateChange> {
}