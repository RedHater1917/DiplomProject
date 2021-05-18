package com.company.equipmentrecords.service;

import com.company.equipmentrecords.entity.GraphObject;
import com.haulmont.bali.db.QueryRunner;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service(GraphService.NAME)
public class GraphServiceBean implements GraphService {
    @Inject
    private Persistence persistence;

    @Override
    public <T,F> HashSet<GraphObject<T,F>> getDataForGraph(String col1, Class<T> col1Class, String col2,Class<F> col2Class) {
        HashSet<GraphObject<T,F>> result;
        final Transaction transaction = persistence.createTransaction();
        try {
            QueryRunner runner = new QueryRunner(persistence.getDataSource());
            result = runner.query("select PRODUCED_PRODUCTION," +
                            "(SELECT FIO FROM EQUIPMENTRECORDS_WORKER WHERE ID = WORKER_ID) " +
                            "from EQUIPMENTRECORDS_EFFICIENCY_INDICATORS",
                    rs -> {
                        HashSet<GraphObject<T,F>> rows = new HashSet<>();
                        while (rs.next()) {
                            rows.add(new GraphObject<>(rs.getObject(1, col1Class)
                                    ,rs.getObject(2, col2Class)));
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