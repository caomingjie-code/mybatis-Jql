package com.javaoffers.batis.modelhelper.fun.condition.where;

import com.javaoffers.batis.modelhelper.fun.ConditionTag;
import com.javaoffers.batis.modelhelper.fun.condition.IgnoreAndOrWordCondition;
import com.javaoffers.batis.modelhelper.fun.condition.where.WhereOnCondition;

/**
 * @Description: exists sql 语句
 * @Auther: create by cmj on 2022/5/3 02:54
 */
public class LimitWordCondition<V> extends WhereOnCondition<V> implements IgnoreAndOrWordCondition {

    private int pageNum;

    private int pageSize;

    private int startIndex;

    private int len;

    public LimitWordCondition(int pageNum, int pageSize) {
        if(pageNum < 1){
            pageNum = 1;
        }
        if(pageSize < 1){
            pageSize = 1;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startIndex = (pageNum-1) * pageSize;
        this.len = pageSize;
        cleanAndOrTag();
    }

    @Override
    public String getSql() {
        String startIndexTag = getNextTag();
        String lenTag = getNextTag();
        this.getParams().put(startIndexTag, this.startIndex);
        this.getParams().put(lenTag, this.len);
        return getTag().getTag() +" #{" + startIndexTag +"} , #{" + lenTag +"}";
    }

    @Override
    public ConditionTag getTag() {
        return ConditionTag.LIMIT;
    }
}
