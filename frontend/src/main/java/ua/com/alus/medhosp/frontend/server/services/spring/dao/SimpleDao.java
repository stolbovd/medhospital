package ua.com.alus.medhosp.frontend.server.services.spring.dao;

import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.hector.api.beans.*;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;
import ua.com.alus.medhosp.frontend.shared.AbstractDTO;
import ua.com.alus.medhosp.frontend.shared.SuperColumn;
import ua.com.alus.medhosp.prototype.cassandra.dto.BaseColumns;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Usatov Alexey
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
        if (abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName()) == null) {
            abstractDTO.put(BaseColumns.ENTITY_ID.getColumnName(), String.valueOf(keyspace.createClock()));
        }
        //adding all sent columns
        for (String column : columns) {
            if (abstractDTO.get(column) == null) {
                continue;
            }
            m1.addInsertion(abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName()),
                    cFamilyName, HFactory.createStringColumn(column, abstractDTO.get(column)));
        }

        //adding key also
        m1.addInsertion(abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName()),
                cFamilyName, HFactory.createStringColumn(BaseColumns.ENTITY_ID.getColumnName(), abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName())));

        m1.execute();
    }

    public void saveSuperColumn(D abstractDTO, String... columns) {
        if (columns == null || columns.length == 0) {
            columns = abstractDTO.getColumns();
        }
        Mutator<String> m1 = createMutator();
        if (abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName()) == null) {
            abstractDTO.put(BaseColumns.ENTITY_ID.getColumnName(), String.valueOf(keyspace.createClock()));
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
        columnArrayList.add(HFactory.createStringColumn(BaseColumns.ENTITY_ID.getColumnName(), abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName())));

        HSuperColumn<String, String, String> superColumn =
                HFactory.createSuperColumn(((SuperColumn) abstractDTO).getSuperKeyName(), columnArrayList, ss, ss, ss);
        m1.addInsertion(abstractDTO.get(BaseColumns.ENTITY_ID.getColumnName()), cFamilyName, superColumn);
        m1.execute();
    }

    public List<D> find(CassandraSearch cassandraSearch) {
        if (dtoObject instanceof SuperColumn) {
            return findSuper(cassandraSearch);
        } else {
            if (cassandraSearch.getSimpleNames2Values().size() > 0) {
                return findSimpleIndexed(cassandraSearch);
            }
            return findSimple(cassandraSearch);
        }
    }

    private List<D> findSimple(CassandraSearch cassandraSearch) {
        ArrayList<D> abstractDTOs = new ArrayList<D>();
        try {
            RangeSlicesQuery<String, String, String> rangeSlicesQuery =
                    HFactory.createRangeSlicesQuery(keyspace, ss, ss, ss);
            rangeSlicesQuery.setColumnFamily(cFamilyName);
            rangeSlicesQuery.setColumnNames(dtoObject.getColumns());
            //100 rows by default
            rangeSlicesQuery.setRange("", "", false, cassandraSearch.getCount());
            rangeSlicesQuery.setKeys(cassandraSearch.getKeyStart(), cassandraSearch.getKeyEnd());
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

    private List<D> findSimpleIndexed(CassandraSearch cassandraSearch) {
        ArrayList<D> abstractDTOs = new ArrayList<D>();
        try {
            IndexedSlicesQuery<String, String, String> indexedSlicesQuery =
                    HFactory.createIndexedSlicesQuery(keyspace, ss, ss, ss);
            indexedSlicesQuery.setColumnFamily(cFamilyName);
            indexedSlicesQuery.setColumnNames(dtoObject.getColumns());
            //100 rows by default
            indexedSlicesQuery.setRange("", "", false, cassandraSearch.getCount());
            for (String columnName : cassandraSearch.getSimpleNames2Values().keySet()) {
                indexedSlicesQuery.addEqualsExpression(columnName, cassandraSearch.getSimpleNames2Values().get(columnName));
            }
            QueryResult<OrderedRows<String, String, String>> result = indexedSlicesQuery.execute();
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

    private List<D> findSuper(CassandraSearch cassandraSearch) {
        ArrayList<D> abstractDTOs = new ArrayList<D>();
        try {
            RangeSuperSlicesQuery<String, String, String, String> rangeSliceQuery =
                    HFactory.createRangeSuperSlicesQuery(keyspace, ss, ss, ss, ss);
            rangeSliceQuery.setColumnFamily(cFamilyName);
            rangeSliceQuery.setColumnNames(cassandraSearch.getSuperColumnsName());
            //100 row by default
            rangeSliceQuery.setRange("", "", false, cassandraSearch.getCount());
            rangeSliceQuery.setKeys(cassandraSearch.getKeyStart(), cassandraSearch.getKeyEnd());
            QueryResult<OrderedSuperRows<String, String, String, String>> result = rangeSliceQuery.execute();
            OrderedSuperRows<String, String, String, String> orderedSuperRows = result.get();
            for (SuperRow<String, String, String, String> row : orderedSuperRows) {
                if (cassandraSearch.getSuperColumnsName().length != 0) {
                    for (String superColumnName : cassandraSearch.getSuperColumnsName()) {
                        HSuperColumn<String, String, String> superColumn =
                                row.getSuperSlice().getColumnByName(superColumnName);
                        if (superColumn == null) {
                            continue;
                        }
                        abstractDTOs.add(createDto(superColumn));
                    }
                } else {
                    for (HSuperColumn<String, String, String> superColumn : row.getSuperSlice().getSuperColumns()) {
                        if (superColumn == null) {
                            continue;
                        }
                        abstractDTOs.add(createDto(superColumn));
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return abstractDTOs;
    }

    private D createDto(HSuperColumn<String, String, String> superColumn) throws IllegalAccessException, InstantiationException {
        D currentDto = dtoClass.newInstance();
        for (HColumn<String, String> simpleColumns : superColumn.getColumns()) {
            currentDto.put(simpleColumns.getName(), String.valueOf(simpleColumns.getValue()));
        }
        ((SuperColumn) currentDto).setSuperKeyName(superColumn.getName());
        return currentDto;
    }

    public Integer removeSelected(List<String> keys) {
        if (dtoObject instanceof SuperColumn) {
            return removeSelectedSuper(keys);
        } else {
            return removeSelectedSimple(keys);
        }
    }

    private Integer removeSelectedSuper(List<String> keys) {
        Mutator<String> mutator = createMutator();
        for (String key : keys) {
            mutator.addSuperDelete(key, cFamilyName, ((SuperColumn) dtoObject).getSuperKeyName(), ss);
        }
        mutator.execute();
        return keys.size();
    }

    public Integer removeSelectedSuperBySuperKeyName(String key, String superKeyName) {
        Mutator<String> mutator = createMutator();
        mutator.addSuperDelete(key, cFamilyName, superKeyName, ss);
        mutator.execute();
        return 1;
    }

    private Integer removeSelectedSimple(List<String> keys) {
        Mutator<String> mutator = createMutator();
        for (String key : keys) {
            mutator.addDeletion(key, cFamilyName);
        }
        mutator.execute();
        return keys.size();
    }
}
