package com.company.equipmentrecords.web.screens.worker;

import com.haulmont.cuba.gui.screen.*;
import com.company.equipmentrecords.entity.Worker;

@UiController("equipmentrecords_Worker.browse")
@UiDescriptor("worker-browse.xml")
@LookupComponent("workersTable")
@LoadDataBeforeShow
public class WorkerBrowse extends StandardLookup<Worker> {
}