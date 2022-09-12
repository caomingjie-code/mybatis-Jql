package com.javaoffers.batis.modelhelper.utils;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: table description
 * @Auther: create by cmj on 2022/5/4 00:00
 */
public class TableInfo {

    /**
     * table name
     */
    private String tableName;

    /**
     * K: table field
     * V: Corresponding table field information, obtained by database query
     * lazy loading storage
     */
    private Map<String,ColumnInfo> primaryColNames = new LinkedHashMap<>();

    /**
     * K: table field
     * V: Corresponding table field information, obtained by database query
     * lazy loading storage
     */
    private Map<String,ColumnInfo> colNames = new LinkedHashMap<>();

    /**
     * K: attribute name (also alias)
     * V: the corresponding table field, generated by the class attribute
     * lazy loading storage
     */
    private Map<String,String> colNameOfModel = new LinkedHashMap<>();

    /**
     * K: attribute name (also alias)
     * V: the corresponding table field, generated by the class attribute
     * lazy loading storage
     */
    private Map<String, List<Field>> colNameOfModelField = new LinkedHashMap<>();

    /**
     * K: The method name obtained by lamda
     * V: The corresponding table fields are generated by lamda
     * lazy loading storage
     */
    private Map<String,String> colNameOfGetter = new ConcurrentHashMap<>();

    /**
     * Store field information
     */
    private Set<ColumnInfo> columnInfos = new LinkedHashSet<>();

    public String getTableName() {
        return tableName;
    }

    public Map<String, ColumnInfo> getColNames() {
        return colNames;
    }

    public Set<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public TableInfo(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, String> getColNameOfModel() {
        return colNameOfModel;
    }

    public Map<String, String> getMethodNameMappingFieldNameOfGetter() {
        return colNameOfGetter;
    }

    public Map<String, List<Field>> getColNameOfModelField() {
        return colNameOfModelField;
    }

    public Map<String, List<Field>> putColNameOfModelField(String colName, Field field) {
        List<Field> fields = colNameOfModelField.get(colName);
        if(fields == null){
            fields = new LinkedList<>();
            colNameOfModelField.put(colName, fields);
        }
        fields.add(field);
        return colNameOfModelField;
    }

    public boolean isAutoincrement(String colName){
        ColumnInfo columnInfo = getColumnInfo(colName);
        return columnInfo.isAutoincrement();
    }

    public Object getDefaultValue(String colName) {
        return getColumnInfo(colName).getDefaultValue();
    }

    private ColumnInfo getColumnInfo(String colName) {
        ColumnInfo columnInfo = this.getColNames().get(colName);
        Assert.isTrue(columnInfo != null,"the "+colName+"col name is empty");
        return columnInfo;
    }

    public Map<String, ColumnInfo> getPrimaryColNames() {
        return primaryColNames;
    }
}