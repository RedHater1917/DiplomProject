package com.company.equipmentrecords.service;

import com.company.equipmentrecords.entity.*;
import com.haulmont.bali.db.QueryRunner;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service(GraphService.NAME)
public class GraphServiceBean implements GraphService {
    @Inject
    private Persistence persistence;

    @Override
    public <T,F> List<GraphObject<T, F>> getDataForGraph(GraphQuerySettings settings, Class<T> valueColClass, Class<F> groupColClass){
        ArrayList<GraphObject<T,F>> result;
        String queryString = settings.getQuery();
        final Transaction transaction = persistence.createTransaction();
        try {
            QueryRunner runner = new QueryRunner(persistence.getDataSource());
            result = runner.query(queryString,
                    rs -> {
                        ArrayList<GraphObject<T,F>> rows = new ArrayList<>();
                        while (rs.next()) {
                            rows.add(new GraphObject<>(rs.getObject(2, valueColClass)
                                    ,rs.getObject(1, groupColClass)));
                        };
                        return rows;
                    });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            transaction.end();
        }
        return result;
    }

    @Override
    public List<TimeGraphObject> getDataForTimeGraph(TimeGraphQuerySettings settings) {
        ArrayList<TimeGraphObject> result;
        String queryString = settings.getQuery();
        final Transaction transaction = persistence.createTransaction();
        try {
            QueryRunner runner = new QueryRunner(persistence.getDataSource());
            result = runner.query(queryString,
                    rs -> {
                        ArrayList<TimeGraphObject> rows = new ArrayList<>();
                        while (rs.next()) {
                            rows.add(new TimeGraphObject(rs.getBigDecimal(2),
                                    rs.getObject(1, LocalDate.class),
                                    rs.getBigDecimal(3)));
                        };
                        return rows;
                    });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            transaction.end();
        }
        return result;
    }
}