package com.company.equipmentrecords.web.screens.equipment;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.Equipment;

@UiController("equipmentrecords_Equipment.browse")
@UiDescriptor("equipment-browse.xml")
@LookupComponent("equipmentsTable")
@LoadDataBeforeShow
public class EquipmentBrowse extends StandardLookup<Equipment> {
}