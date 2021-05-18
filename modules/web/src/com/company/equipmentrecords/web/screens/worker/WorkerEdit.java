package com.company.equipmentrecords.web.screens.worker;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.Worker;

@UiController("equipmentrecords_Worker.edit")
@UiDescriptor("worker-edit.xml")
@EditedEntityContainer("workerDc")
@LoadDataBeforeShow
public class WorkerEdit extends StandardEditor<Worker> {
}