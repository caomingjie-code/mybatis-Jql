package com.javaoffers.brief.modelhelper.fun.crud;

import com.javaoffers.brief.modelhelper.fun.GetterFun;

import java.io.Serializable;

/**
 * @Description: on 拼接sql 条件
 * @Auther: create by cmj on 2022/5/2 00:56
 * sql: selct xx from a left join b on a.col = b.col
 */
public interface FirstOnFun<M1,M2, C extends GetterFun<M1, Object> & Serializable, C2 extends GetterFun<M2, Object> & Serializable, V>{

    /**
     * 添加等值关系 =
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V, ?> oeq(C col, C2 col2);

    /**
     * 添加不等值关系 !=
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V, ?> oueq(C col, C2 col2);

    /**
     * 大于  >
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V, ?> ogt(C col, C2 col2);

    /**
     * 小于 <
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V, ?> olt(C col, C2 col2);

    /**
     * 大于等于  >=
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V, ?> ogtEq(C col, C2 col2);

    /**
     * 小于等于 <=
     * @param col
     * @param col2
     * @return
     */
    public SmartOnFun<M1,M2, C, C2, V, ?> oltEq(C col, C2 col2);

}
