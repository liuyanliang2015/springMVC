package com.bert.common.batis.criterion;

import java.io.Serializable;

import com.bert.common.batis.exception.CommonBatisException;

/**
 * a interface should be implemented to represent a constraint in a criteria
 * <p/>
 * *
 */
public interface Criterion extends Serializable {

    /**
     * Render the SQL fragment
     *
     */
    public String getStatement() throws CommonBatisException;

}