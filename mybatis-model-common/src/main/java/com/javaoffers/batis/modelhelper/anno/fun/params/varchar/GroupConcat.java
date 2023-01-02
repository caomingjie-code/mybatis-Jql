package com.javaoffers.batis.modelhelper.anno.fun.params.varchar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: GROUP_CONCAT([DISTINCT] column1 [ORDER BY column2 ASC\DESC] [SEPARATOR seq]);
 * @author: create by cmj on 2023/1/1 18:02
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GroupConcat {

    public static final String TAG = "GROUP_CONCAT";

    /**
     * Whether to add distinct keywords
     */
    boolean distinct() default false;

    /**
     * col name for order by operation
     * @return
     */
    OrderBy orderBy() default @OrderBy;

    /**
     * SEPARATOR seq
     */
    String separator() default "";

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public static @interface OrderBy{

        /**
         * order by colName .
         */
        String colName() default "";

        /**
         * sort model.
         */
        Sort sort() default Sort.ASC;
    }

    public static enum Sort{
        ASC,
        DESC
        ;
    }
}