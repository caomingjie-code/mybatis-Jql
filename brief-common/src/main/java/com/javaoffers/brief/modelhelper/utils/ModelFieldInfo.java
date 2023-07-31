package com.javaoffers.brief.modelhelper.utils;

import com.javaoffers.brief.modelhelper.anno.BaseUnique;
import com.javaoffers.brief.modelhelper.exception.ParseModelException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 避免反射.
 * @author mingJie
 */
public class ModelFieldInfo {

    private boolean isModelClass;

    /**
     * 声明的model类型，该field属于此model中的一个field字段
     */
    private Class modelClass;

    /**
     * field真实类型. 如果是数组或者集合，则是真实的类型为元素类型。
     */
    private Class fieldGenericClass;

    /**
     * field的类型,可能会是集合
     */
    private Class fieldType;

    /**
     * tableInfo
     */
    private TableInfo tableInfo;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 反射field字段
     */
    private Field field;

    /**
     * setter方法
     */
    private Setter setter;

    /**
     * getter方法
     */
    private Getter getter;

    /**
     * field名称
     */
    private String fieldName;

    /**
     * colName as  aliasName
     */
    private String aliasName;

    /**
     * @BaseUnique
     */
    private boolean isUniqueField;

    /**
     * 是否是抽象类型，通常用于Set,List
     */
    private boolean fieldTypeIsAbstract;

    /**
     * list / set 构造方法. 当fieldType为List/Set时才有效
     */
    private Newc newc;

    public ModelFieldInfo(Field field, Class modelClass) {
        this.field = field;
        this.modelClass = modelClass;
        init();
    }

    private void init(){
        this.field.setAccessible(true);
        try {
            this.isModelClass = Utils.isBaseModel(this.field);
            this.setter = LambdaCreateUtils.createSetter(field);
            this.getter = LambdaCreateUtils.createGetter(field);
            this.fieldName = this.field.getName();
            this.tableInfo = TableHelper.getTableInfo(this.modelClass);
            this.tableName = this.tableInfo.getTableName();
            this.aliasName = Utils.getSpecialColName(this.tableName, tableInfo.getDbType(), this.fieldName);
            this.isUniqueField = this.field.getDeclaredAnnotation(BaseUnique.class) != null;
            this.fieldGenericClass = Utils.getGenericityClass(field);
            this.fieldType = this.field.getType();
            this.fieldTypeIsAbstract = this.fieldType.isInterface();
            if(this.fieldTypeIsAbstract){
                if(List.class.isAssignableFrom(this.fieldType)){
                    newc = ArrayList::new;
                }else if(Set.class.isAssignableFrom(this.fieldType)){
                    newc = HashSet::new;
                } else if(Collection.class.isAssignableFrom(this.fieldType)){
                    newc = ArrayList::new;
                }
            }else if(Collection.class.isAssignableFrom(this.fieldType)){
                newc = LambdaCreateUtils.createConstructor(this.fieldType);
            }

        }catch (Throwable e){
            e.printStackTrace();
            throw new ParseModelException(e.getMessage());
        }
    }

    public Class getModelClassOfField() {
        if(!this.isModelClass){
            return null;
        }
        return this.fieldGenericClass;
    }

    public boolean isModelClass(){
        return this.isModelClass;
    }

    public Field getField() {
        return field;
    }

    public Setter getSetter() {
        return setter;
    }

    public Class getModelClass() {
        return modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public boolean isUniqueField() {
        return isUniqueField;
    }

    public Class getFieldGenericClass() {
        return fieldGenericClass;
    }

    public Getter getGetter() {
        return getter;
    }

    public boolean isAbstractFieldType() {
        return fieldTypeIsAbstract;
    }

    /**
     * 该对象为List/Set/Collection
     * @return
     */
    public Object getNewc(){
        return newc.newc();
    }
}
