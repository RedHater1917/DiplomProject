package com.company.equipmentrecords.web.screens.graph;

import com.company.equipmentrecords.entity.ColumnInfo;
import com.company.equipmentrecords.entity.InstancesForGraphs;
import com.company.equipmentrecords.entity.GraphQuerySettings;
import com.company.equipmentrecords.service.GraphService;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.executors.BackgroundTask;
import com.haulmont.cuba.gui.executors.BackgroundTaskHandler;
import com.haulmont.cuba.gui.executors.BackgroundWorker;
import com.haulmont.cuba.gui.executors.TaskLifeCycle;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@UiController("equipmentrecords_Graph")
@UiDescriptor("Graph.xml")
public class Graph extends Screen {
    @Inject
    private SerialChart graph;
    @Inject
    private LookupField<String> column1;
    @Inject
    private LookupField<String> column2;
    @Inject
    private GraphService graphService;
    @Inject
    private BackgroundWorker backgroundWorker;
    @Inject
    private Button draw;
    @Inject
    private ProgressBar load;
    @Inject
    private PopupView popupView;
    @Inject
    private Notifications notifications;

    private Map<String, ColumnInfo> columnInfoMap = new HashMap<>();

    private GraphQuerySettings settings = new GraphQuerySettings();
    @Inject
    private PopupButton colFunc;
    @Inject
    private CheckBox col1Order;
    @Inject
    private CheckBox col2Order;

    public void drawGraph(){
        changeVisible(false);
        ListDataProvider dataProvider = new ListDataProvider();
        BackgroundTask<Integer, Void> task = new BackgroundTask<Integer, Void>(60, this) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                ColumnInfo valueColumn = columnInfoMap.get(column1.getValue());
                ColumnInfo groupColumn = columnInfoMap.get(column2.getValue());
                settings.setValueCol(valueColumn.getQueryCol());
                settings.setGroupCol(groupColumn.getQueryCol());
                if(settings.getColFunc()!= null && settings.getColFunc().equals("AVG")){
                    valueColumn.setColumnClass(BigDecimal.class);
                }
                graphService.getDataForGraph(settings,valueColumn.getColumnClass(),
                        groupColumn.getColumnClass()).forEach(graphObject ->
                    dataProvider.addItem(new MapDataItem().add("x", graphObject.getValue()).
                                                           add("y", graphObject.getCategory())));
                return null;
            }
            @Override
            public void done(Void result) {
                changeVisible(true);
                graph.setDataProvider(dataProvider);
            }

            @Override
            public void canceled() {
                changeVisible(true);
                notifications.create(Notifications.NotificationType.TRAY).
                        withCaption("ERROR").withDescription("Something went wrong in a process").show();
            }
        };
        BackgroundTaskHandler<Void> taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();

    }

    public void checkForEnableDraw(){
        if(this.column1.getValue()!=null && this.column2.getValue()!=null) this.draw.setEnabled(true);
    }
    @Subscribe("column1")
    public void onColumn1ValueChange(HasValue.ValueChangeEvent event) {
        checkForEnableDraw();
    }

    @Subscribe("column2")
    public void onColumn2ValueChange(HasValue.ValueChangeEvent event) {
        checkForEnableDraw();
    }

    @Subscribe("graph_choose")
    public void onGraph_chooseValueChange1(HasValue.ValueChangeEvent<InstancesForGraphs> event) {
        this.column1.setValue(null);
        this.column2.setValue(null);
    }

    private void changeVisible(boolean done){
        draw.setEnabled(done);
        load.setVisible(!done);
        graph.setVisible(done);
    }
    @Subscribe("button")
    protected void onButtonClick(Button.ClickEvent event) {
        popupView.setPopupVisible(true);
    }

    @Subscribe("graph_choose")
    public void onGraph_chooseValueChange(HasValue.ValueChangeEvent<InstancesForGraphs> event) {
        settings.setEntity(Objects.requireNonNull(event.getValue()).name());
        List<String> keysList = new ArrayList<>(event.getValue().columnInfoMap().keySet());
        columnInfoMap = event.getValue().columnInfoMap();
        column1.setOptionsList(keysList);
        column2.setOptionsList(keysList);
    }

    @Subscribe("col1Order")
    public void onCol1OrderValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        settings.setValueColOrder(Objects.requireNonNull(event.getValue()));
        col2Order.setValue(false);
    }

    @Subscribe("col2Order")
    public void onCol2OrderValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        settings.setGroupColOrder(Objects.requireNonNull(event.getValue()));
        col1Order.setValue(false);
    }

    @Subscribe("changeColFunc")
    public void onChangeColFunc(Action.ActionPerformedEvent event) {
        settings.setColFunc(event.getComponent().getId());
        colFunc.setCaption(event.getComponent().getId());
    }

    @Subscribe("colFuncCheckBox")
    public void onColFuncCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        colFunc.setVisible(Objects.requireNonNull(event.getValue()));
        if(!Objects.requireNonNull(event.getValue())){
            settings.setColFunc(null);
        }
    }
}
