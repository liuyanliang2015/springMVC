package com.bert.common.batis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bert.common.batis.criterion.Criterion;

public class Criteria {

    private List<Criterion> criterions = new ArrayList<Criterion>();

    public List<Criterion> getCriteria() {
        return Collections.unmodifiableList(criterions);
    }

    public void setCriteria(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public Criteria add(Criterion criterion) {
    	criterions.add(criterion);
        return this;
    }

}
