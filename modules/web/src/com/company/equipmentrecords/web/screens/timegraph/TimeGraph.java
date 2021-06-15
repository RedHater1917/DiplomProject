package com.company.equipmentrecords.web.screens.timegraph;

import com.company.equipmentrecords.entity.EfficiencyIndicators;
import com.company.equipmentrecords.entity.Equipment;
import com.company.equipmentrecords.entity.TimeGraphQuerySettings;
import com.company.equipmentrecords.entity.Worker;
import com.company.equipmentrecords.service.GraphService;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.BackgroundTaskHandler;
import com.haulmont.cuba.gui.executors.BackgroundWorker;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@UiController("equipmentrecords_TimeGraph")
@UiDescriptor("TimeGraph.xml")
public class TimeGraph extends Screen {
    @Inject
    private Notifications notifications;
    @Inject
    private GraphService graphService;
    @Inject
    private BackgroundWorker backgroundWorker;
    @Inject
    private SerialChart graph;
    @Inject
    private ProgressBar load;
    @Inject
    private Button draw;
    @Inject
    private DateField<LocalDate> beg;
    @Inject
    private DateField<LocalDate> end;
    @Inject
    private PickerField<Equipment> firstCompareEquip;
    @Inject
    private PickerField<Equipment> secondCompareEquip;
    @Inject
    private PickerField<Worker> firstCompareWorker;
    @Inject
    private PickerField<Worker> secondCompareWorker;
    @Inject
    private Metadata metadata;
    @Inject
    private InstanceContainer<EfficiencyIndicators> compareDc1;
    @Inject
    private InstanceContainer<EfficiencyIndicators> compareDc2;
    @Inject
    private RadioButtonGroup<Integer> queryType;
    @Inject
    private HBoxLayout querySetting;

    private TimeGraphQuerySettings settings = new TimeGraphQuerySettings();

    private Map<String,Integer> optionsMap = new HashMap<String,Integer>(){
        {
            put("Equipment efficiency comparing",1);
            put("Worker efficiency comparing",2);
            put("Equipment breakdowns count comparing",3);
        }
    };
    private PickerField<? extends BaseUuidEntity> first;
    private PickerField<? extends BaseUuidEntity> second;

    public void drawGraph(){
        changeVisibleLoadComponents(false);
        ListDataProvider dataProvider = new ListDataProvider();
        String oldCondition = settings.getDateCondition();
        String oldColQuery = settings.getCol1Query();
        StringBuilder string = new StringBuilder(oldCondition);
        string.append("'").append(beg.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("'").append(" AND ")
                .append("'").append(end.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).append("'")
                .append(" GROUP BY ").append(settings.getDateCol())
                .append(" ORDER BY ").append(settings.getDateCol());
        settings.setDateCondition(string.toString());
        settings.setCol1Query(formatColumnQuery(settings.getCol1Query(),first.getValue().getId()));
        if(second.getValue() != null){
            settings.setCol2Query(formatColumnQuery(settings.getCol2Query(),second.getValue().getId()));
        }
        BackgroundTask<Integer, Void> task = new BackgroundTask<Integer, Void>(60, this) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                graphService.getDataForTimeGraph(settings).forEach(timeGraphObject ->
                    dataProvider.addItem(new MapDataItem().
                            add("value1", timeGraphObject.getValue()).
                            add("category",timeGraphObject.getCategory()).
                            add("value2", timeGraphObject.getAdditionalValue())));
                return null;
            }
            @Override
            public void done(Void result) {
                changeVisibleLoadComponents(true);
                settings.setDateCondition(oldCondition);
                settings.setCol1Query(oldColQuery);
                if(second.getValue() !=null){
                    settings.setCol2Query(oldColQuery);
                }
                graph.setDataProvider(dataProvider);
            }

            @Override
            public void canceled() {
                changeVisibleLoadComponents(true);
                notifications.create(Notifications.NotificationType.TRAY).
                        withCaption("ERROR").withDescription("Something went wrong in a process").show();
            }
        };
        BackgroundTaskHandler<Void> taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();

    }
    private String formatColumnQuery(String columnQuery, UUID id){
        StringBuilder result = new StringBuilder(columnQuery);
        result.append("'").append(id).append("'").append(" AND ").append(settings.getDateCol())
                .append(" = o.").append(settings.getDateCol()).append(")");
        return result.toString();
    }
    @Subscribe
    public void onInit(InitEvent event) {
        EfficiencyIndicators efficiencyIndicators = metadata.create(EfficiencyIndicators.class);
        EfficiencyIndicators efficiencyIndicators2 = metadata.create(EfficiencyIndicators.class);
        compareDc1.setItem(efficiencyIndicators);
        compareDc2.setItem(efficiencyIndicators2);
        queryType.setOptionsMap(optionsMap);
    }

    @Subscribe("queryType")
    public void onQueryTypeValueChange(HasValue.ValueChangeEvent<Integer> event) {
        querySetting.setEnabled(true);
        switch (Objects.requireNonNull(event.getValue())){
            case 1:{
                first = firstCompareEquip;
                second = secondCompareEquip;
                changeVisiblePickerFields(true);
                settings.setDateCol("DATE");
                settings.setCol1Query("(SELECT AVG(PRODUCED_PRODUCTION) " +
                        "FROM EQUIPMENTRECORDS_EFFICIENCY_INDICATORS WHERE EQUIPMENT_ID = ");
                if(second.getValue() == null){
                    settings.setCol2Query("(SELECT AVG(PRODUCED_PRODUCTION) " +
                            "FROM EQUIPMENTRECORDS_EFFICIENCY_INDICATORS WHERE DATE = o.DATE" +
                            " GROUP BY DATE) ");
                }else{
                    settings.setCol2Query(settings.getCol1Query());
                }
                settings.setEntity("EFFICIENCY_INDICATORS o");
                settings.setDateCondition(" WHERE DATE BETWEEN ");
            }
            break;
            case 2:{
                first = firstCompareWorker;
                second = secondCompareWorker;
                changeVisiblePickerFields(false);
                settings.setDateCol("DATE");
                settings.setCol1Query("(SELECT AVG(PRODUCED_PRODUCTION) " +
                        "FROM EQUIPMENTRECORDS_EFFICIENCY_INDICATORS WHERE WORKER_ID = ");
                if(second.getValue() == null){
                    settings.setCol2Query("(SELECT AVG(PRODUCED_PRODUCTION) " +
                            "FROM EQUIPMENTRECORDS_EFFICIENCY_INDICATORS WHERE DATE = o.DATE" +
                            " GROUP BY DATE) ");
                }else{
                    settings.setCol2Query(settings.getCol1Query());
                }
                settings.setEntity("EFFICIENCY_INDICATORS o");
                settings.setDateCondition(" WHERE DATE BETWEEN ");
            }
            break;
            case 3:{
                first = firstCompareEquip;
                second = secondCompareEquip;
                changeVisiblePickerFields(true);
                settings.setDateCol("DATE_OF_CHANGE");
                settings.setCol1Query("(SELECT COUNT(STATE) " +
                        "FROM EQUIPMENTRECORDS_EQUIPMENT_STATE_CHANGE WHERE STATE = 'broken' AND EQUIPMENT_ID = ");
                if(second.getValue() == null){
                    settings.setCol2Query("AVG((SELECT COUNT(STATE)" +
                            " FROM EQUIPMENTRECORDS_EQUIPMENT_STATE_CHANGE WHERE STATE = 'broken' " +
                            " AND DATE_OF_CHANGE = o.DATE_OF_CHANGE"+
                            " GROUP BY DATE_OF_CHANGE)) ");
                }else{
                    settings.setCol2Query(settings.getCol1Query());
                }
                settings.setEntity("EQUIPMENT_STATE_CHANGE o");
                settings.setDateCondition(" WHERE DATE_OF_CHANGE BETWEEN ");
            }
            break;
        }
    }


    private void changeVisibleLoadComponents(boolean done){
        draw.setEnabled(done);
        load.setVisible(!done);
        graph.setVisible(done);
    }

    private void changeVisiblePickerFields(boolean isEquipment){
        firstCompareWorker.setVisible(!isEquipment);
        secondCompareWorker.setVisible(!isEquipment);
        firstCompareEquip.setVisible(isEquipment);
        secondCompareEquip.setVisible(isEquipment);
    }

    public void checkForEnableDraw(){
        this.draw.setEnabled(this.beg.getValue() != null && this.end.getValue() != null
                && this.first.getValue() != null && this.queryType.getValue() != null);
        onQueryTypeValueChange(new HasValue.ValueChangeEvent<Integer>(queryType,queryType.getValue(),queryType.getValue()));
    }

    @Subscribe("beg")
    public void onBegValueChange(HasValue.ValueChangeEvent<Date> event) {
        checkForEnableDraw();
    }

    @Subscribe("secondCompareWorker")
    public void onSecondCompareWorkerValueChange(HasValue.ValueChangeEvent<Worker> event) {
        checkForEnableDraw();
    }

    @Subscribe("secondCompareEquip")
    public void onSecondCompareEquipValueChange(HasValue.ValueChangeEvent<Equipment> event) {
        checkForEnableDraw();
    }

    @Subscribe("end")
    public void onEndValueChange(HasValue.ValueChangeEvent<Date> event) {
        checkForEnableDraw();
    }

}