package com.javaoffers.brief.modelhelper.fun.crud.impl;

import com.javaoffers.brief.modelhelper.fun.AggTag;
import com.javaoffers.brief.modelhelper.fun.Condition;
import com.javaoffers.brief.modelhelper.fun.ConditionTag;
import com.javaoffers.brief.modelhelper.fun.GetterFun;
import com.javaoffers.brief.modelhelper.fun.condition.JoinTableCondition;
import com.javaoffers.brief.modelhelper.fun.crud.JoinFun;
import com.javaoffers.brief.modelhelper.fun.condition.select.SelectColumnCondition;
import com.javaoffers.brief.modelhelper.fun.crud.OnFun;
import com.javaoffers.brief.modelhelper.utils.TableHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description: join 功能实现,以字符串方式输入为字段名称
 * @Auther: create by cmj on 2022/5/2 02:11
 */
public class JoinFunImpl<M1,M2,V> implements JoinFun<M1, M2, GetterFun<M2, Object>, V> {

    private LinkedList<Condition> conditions;
    private Class<M1> m1Class;
    private Class<M2> m2Class;
    private String table2Name;

    public JoinFunImpl(Class<M1> mc, Class<M2> m2c, LinkedList<Condition> conditions, ConditionTag tag) {
        this.m1Class = mc;
        this.m2Class = m2c;
        this.conditions = conditions;
        this.table2Name = TableHelper.getTableName(m2c);
        this.conditions.add(new JoinTableCondition(this.table2Name,tag));
    }

    /**
     * 添加查询字段
     * @param colSql
     * @return
     */
    @Override
    @SafeVarargs
    public final JoinFunImpl<M1, M2, V> col(String... colSql) {
        Stream.of(colSql).forEach(col->{conditions.add(new SelectColumnCondition( col));});
        return this;
    }

    @Override
    @SafeVarargs
    public final JoinFunImpl<M1, M2, V> col(GetterFun<M2, Object>... cols) {
        Stream.of(cols).forEach(col->conditions.add(new SelectColumnCondition( col)));
        return this;
    }

    @Override
    @SafeVarargs
    public final JoinFun<M1, M2, GetterFun<M2, Object>, V> col(boolean condition, GetterFun<M2, Object>... cols) {
        if(condition){
            col(cols);
        }
        return this;
    }

    @Override
    @SafeVarargs
    public final JoinFun<M1, M2, GetterFun<M2, Object>, V> col(AggTag aggTag, GetterFun<M2, Object>... cols) {
        Stream.of(cols).forEach(col->{
            Pair<String, String> colAgg = TableHelper.getSelectAggrColStatement(col);
            this.conditions.add(new SelectColumnCondition(aggTag.name()+"("+colAgg.getLeft()+") as " + colAgg.getRight()));
        });
        return this;
    }

    @Override
    @SafeVarargs
    public final JoinFun<M1, M2, GetterFun<M2, Object>, V> col(boolean condition, AggTag aggTag, GetterFun<M2, Object>... col) {
        if(condition){
            col(aggTag,col);
        }
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(AggTag aggTag, GetterFun<M2, Object> col, String asName) {
        Pair<String, String> colNameAndAliasName = TableHelper.getColNameAndAliasName(col);
        String colName = colNameAndAliasName.getLeft();
        conditions.add(new SelectColumnCondition(aggTag.name()+"("+colName+") as "
                //join table need SimpleName+asName for diff with main table.
                + this.m2Class.getSimpleName() + asName));
        return this;
    }

    @Override
    public JoinFun<M1, M2, GetterFun<M2, Object>, V> col(boolean condition, AggTag aggTag, GetterFun<M2, Object> col, String asName) {
        if(condition){
            col(aggTag, col, asName);
        }
        return this;
    }

    @Override
    public JoinFunImpl<M1, M2, V> colAll() {
        List<SelectColumnCondition> colAll = TableHelper.getColAllForSelect(m2Class, SelectColumnCondition::new);
        conditions.addAll(colAll);
        return this;
    }

    @Override
    public <C1 extends GetterFun<M1, Object>> OnFun<M1, M2, V,?> on() {
        return  new OnFunImpl(this.m1Class, this.m2Class, this.conditions);
    }

}
