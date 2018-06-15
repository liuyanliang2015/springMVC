package com.bert.common.batis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bert.common.batis.criterion.Order;

public class Condition {
	
	private Integer firstResult;
    private Integer maxResults;
    private List<Order> orderList;
    private List<Criteria> criterias;
    private boolean distinct;

    public Condition() {
        orderList = new ArrayList<Order>();
        criterias = new ArrayList<Criteria>();
    }

    public Condition addOrder(Order order) {
        if (order == null || order.getPropertyName() == null
                || "".equals(order.getPropertyName())) {
            throw new IllegalArgumentException(
                    "order or propertyName can not be null");
        }
        orderList.add(order);
        return this;
    }

    public Criteria add(Criteria criteria) {
    	criterias.add(criteria);
        return criteria;
    }

    public void or(Criteria criteria) {
    	criterias.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = new Criteria();
        criterias.add(criteria);
        return criteria;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public List<Order> getOrderList() {
        return Collections.unmodifiableList(orderList);
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public Integer getFirstResult() {
        return firstResult;
    }

    public List<Criteria> getCriterias() {
        return Collections.unmodifiableList(criterias);
    }

    public void setCriterias(List<Criteria> criterias) {
        this.criterias = criterias;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

}
