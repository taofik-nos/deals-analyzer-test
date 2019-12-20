package com.tao.prtest.dealsanalyzer.dal.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AnalysisSummary {

    @Id
    @GeneratedValue
    public long id;
    public int validDealsCount;
    public int invalidDealsCount;
    public long executionTimeInMilis;
    public String fileName;
}
