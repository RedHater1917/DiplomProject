package com.company.equipmentrecords.web.screens.equipment;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.Equipment;

@UiController("equipmentrecords_Equipment.edit")
@UiDescriptor("equipment-edit.xml")
@EditedEntityContainer("equipmentDc")
@LoadDataBeforeShow
public class EquipmentEdit extends StandardEditor<Equipment> {
}