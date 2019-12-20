package com.tao.prtest.dealsanalyzer.dal.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
//@Table(name="validRowss")
public class ValidRow extends Row{

    public ValidRow(UUID id, String orderingCurrencyCode, String toCurrencyCode, ZonedDateTime timestamp, long dealAmount, String sourceFileName) {
        super(id, orderingCurrencyCode, toCurrencyCode, timestamp, dealAmount, sourceFileName);
    }

    public ValidRow(){
        super();
    }
}
