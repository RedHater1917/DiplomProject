package com.company.equipmentrecords.web.screens.graph;

import com.company.equipmentrecords.entity.InstancesForGraphs;
import com.company.equipmentrecords.service.GraphService;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.components.charts.XYChart;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
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
import java.util.Arrays;
import java.util.Objects;

@UiController("equipmentrecords_Graph")
@UiDescriptor("Graph.xml")
public class Graph extends Screen {
    @Inject
    private Label<String> test;
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

    private Class<?>[] classes;
    //Оно в каком-то виде сейчас работатет
    /*TODO 1.Присрать возможность делать запросы с агрегирующими функицями(AVG, COUNT)
           2.Надо насрать еще пару сущностей
           3.Сделать возможность брать данные и из связанных таблиц, а не только из 1
           3.1 Либо хранением в enum'e этих запросов, либо тянуть эту срань EntityManager'ом
           4. Сделать скрин не таким всратым по верстке
           5. Сделать еще один скрин, где будут сравниваться показатели за разные временные промежутки
           6. Замутить условий для выборки данных на этом скрине
           7. Сделать сортировку по умолчанию в запросе по дате создания
    * */
    public void drawGraph(){
        draw.setEnabled(false);
        graph.setVisible(false);
        load.setVisible(true);
        ListDataProvider dataProvider = new ListDataProvider();
        BackgroundTask<Integer, Void> task = new BackgroundTask<Integer, Void>(10, this) {
            @Override
            public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                graphService.getDataForGraph(column1.getValue(),Integer.class,column2.getValue(),String.class).forEach(graphObject -> {
                    dataProvider.addItem(new MapDataItem().add("x", graphObject.getCordX()).
                                                           add("y", graphObject.getCordY()));
                });
                return null;
            }
            @Override
            public void done(Void result) {
               draw.setEnabled(true);
               load.setVisible(false);
               graph.setVisible(true);
               graph.setDataProvider(dataProvider);
            }
        };
        BackgroundTaskHandler taskHandler = backgroundWorker.handle(task);
        taskHandler.execute();

    }

    @Subscribe("graph_choose")
    public void onGraph_chooseValueChange(HasValue.ValueChangeEvent<InstancesForGraphs> event) {
        test.setValue(Objects.requireNonNull(event.getValue()).name());
        column1.setOptionsList(Arrays.asList(event.getValue().columns()));
        column2.setOptionsList(Arrays.asList(event.getValue().columns()));
        classes = event.getValue().dataTypes();
    }

}