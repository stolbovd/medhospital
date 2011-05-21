package ua.com.alus.medhosp.frontend.server.services.spring.dao;

import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import ua.com.alus.medhosp.frontend.shared.SuperColumn;
import me.prettyprint.hector.api.beans.*;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 02.05.11
 * Time: 10:46
 */
public abstract class SimpleDao<D extends AbstractDTO> extends AbstractDao {
    private String cFamilyName;
    private Class<D> dtoClass;
    private D dtoObject;

    private Mutator<String> mutator;

    @SuppressWarnings("unchecked")
    public SimpleDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.dtoClass = (Class<D>) genericSuperclass
                .getActualTypeArguments()[0];
        try {
            dtoObject = dtoClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setcFamilyName(String cFamilyName) {
        this.cFamilyName = cFamilyName;
    }

    private Mutator<String> createMutator() {
        if (mutator == null) {
            mutator = HFactory.createMutator(keyspace, ss);
        }
        return mutator;
    }

    public void save(D abstractDTO, String... columns) {
        if (abstractDTO instanceof SuperColumn) {
            saveSuperColumn(abstractDTO, columns);
        } else {
            saveSimpleColumn(abstractDTO, columns);
        }
    }

    public void saveSimpleColumn(D abstractDTO, String... columns) {
        if (columns == null || columns.length == 0) {
            columns = abstractDTO.getColumns();
        }
        Mutator<String> m1 = createMutator();
        if (abstractDTO.get(AbstractDTO.KEY) == null) {
            abstractDTO.put(AbstractDTO.KEY, String.valueOf(keyspace.createClock()));
        }
        //adding all sent columns
        for (String column : columns) {
            if (abstractDTO.get(column) == null) {
                continue;
            }
            m1.addInsertion(abstractDTO.get(AbstractDTO.KEY),
                    cFamilyName, HFactory.createStringColumn(column, abstractDTO.get(column)));
        }

        //adding key also
        m1.addInsertion(abstractDTO.get(AbstractDTO.KEY),
                cFamilyName, HFactory.createStringColumn(AbstractDTO.KEY, abstractDTO.get(AbstractDTO.KEY)));

        m1.execute();
    }

    public void saveSuperColumn(D abstractDTO, String... columns) {
        if (columns == null || columns.length == 0) {
            columns = abstractDTO.getColumns();
        }
        Mutator<String> m1 = createMutator();
        if (abstractDTO.get(AbstractDTO.KEY) == null) {
            abstractDTO.put(AbstractDTO.KEY, String.valueOf(keyspace.createClock()));
        }
        //adding all sent columns
        ArrayList<HColumn<String, String>> columnArrayList = new ArrayList<HColumn<String, String>>();
        for (String column : columns) {
            if (abstractDTO.get(column) == null) {
                continue;
            }
            columnArrayList.add(HFactory.createStringColumn(column, abstractDTO.get(column)));
        }
        //adding key too
        columnArrayList.add(HFactory.createStringColumn(AbstractDTO.KEY, abstractDTO.get(AbstractDTO.KEY)));

        HSuperColumn<String, String, String> superColumn =
                HFactory.createSuperColumn(((SuperColumn) abstractDTO).getSuperKeyName(), columnArrayList, ss, ss, ss);
        m1.addInsertion(abstractDTO.get(AbstractDTO.KEY), cFamilyName, superColumn);
        m1.execute();
    }

    public List<D> findAll() {
        if (dtoObject instanceof SuperColumn) {
            return findAllSuper();
        } else {
            return findAllSimple();
        }
    }

    public List<D> findAllSimple() {
        ArrayList<D> abstractDTOs = new ArrayList<D>();
        try {
            RangeSlicesQuery<String, String, String> rangeSlicesQuery =
                    HFactory.createRangeSlicesQuery(keyspace, ss, ss, ss);
            rangeSlicesQuery.setColumnFamily(cFamilyName);
            rangeSlicesQuery.setColumnNames(dtoObject.getColumns());
            rangeSlicesQuery.setRange("", "", false, dtoObject.getColumns().length);
            QueryResult<OrderedRows<String, String, String>> result = rangeSlicesQuery.execute();
            OrderedRows<String, String, String> orderedRows = result.get();

            for (Row<String, String, String> row : orderedRows) {
                if (row.getColumnSlice().getColumns().size() == 0) {
                    continue;
                }
                D currentDto = dtoClass.newInstance();
                for (String column : dtoObject.getColumns()) {
                    HColumn hColumn = row.getColumnSlice().getColumnByName(column);
                    currentDto.put(column, hColumn == null ? "" : String.valueOf(hColumn.getValue()));
                }
                abstractDTOs.add(currentDto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return abstractDTOs;
    }

    public List<D> findAllSuper() {
        ArrayList<D> abstractDTOs = new ArrayList<D>();
        try {
            String superColumnKey = ((SuperColumn) dtoObject).getSuperKeyName();
            RangeSuperSlicesQuery<String, String, String, String> rangeSliceQuery =
                    HFactory.createRangeSuperSlicesQuery(keyspace, ss, ss, ss, ss);
            rangeSliceQuery.setColumnFamily(cFamilyName);
            rangeSliceQuery.setColumnNames(superColumnKey);
            rangeSliceQuery.setRange("", "", false, 1);
            QueryResult<OrderedSuperRows<String, String, String, String>> result = rangeSliceQuery.execute();
            OrderedSuperRows<String, String, String, String> orderedSuperRows = result.get();
            for (SuperRow<String, String, String, String> row : orderedSuperRows) {
                HSuperColumn<String, String, String> superColumn = row.getSuperSlice().getColumnByName(superColumnKey);
                if (superColumn == null) {
                    continue;
                }
                D currentDto = dtoClass.newInstance();
                for (HColumn<String, String> simpleColumns : superColumn.getColumns()) {
                    currentDto.put(simpleColumns.getName(), String.valueOf(simpleColumns.getValue()));
                }
                abstractDTOs.add(currentDto);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return abstractDTOs;
    }

    public Integer removeSelected(List<String> keys) {
        if (dtoObject instanceof SuperColumn) {
            return removeSelectedSuper(keys);
        } else {
            return removeSelectedSimple(keys);
        }
    }

    public Integer removeSelectedSuper(List<String> keys) {
        Mutator<String> mutator = createMutator();
        for (String key : keys) {
            mutator.addSuperDelete(key, cFamilyName, ((SuperColumn) dtoObject).getSuperKeyName(), ss);
        }
        mutator.execute();
        return keys.size();
    }

    public Integer removeSelectedSimple(List<String> keys) {
        Mutator<String> mutator = createMutator();
        for (String key : keys) {
            mutator.addDeletion(key, cFamilyName);
        }
        mutator.execute();
        return keys.size();
    }
}
