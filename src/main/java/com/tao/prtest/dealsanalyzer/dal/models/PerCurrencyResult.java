package com.tao.prtest.dealsanalyzer.dal.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PerCurrencyResult {

    @Id
    public String orderingCurrencyISOCode;
    public int countOfDeals;

    public PerCurrencyResult(){
        this.orderingCurrencyISOCode = "QR";
    }

    public String getOrderingCurrencyISOCode() {
        return orderingCurrencyISOCode;
    }

    public void setOrderingCurrencyISOCode(String orderingCurrencyISOCode) {
        this.orderingCurrencyISOCode = orderingCurrencyISOCode;
    }

    public int getCountOfDeals() {
        return countOfDeals;
    }

    public void setCountOfDeals(int countOfDeals) {
        this.countOfDeals = countOfDeals;
    }
}
