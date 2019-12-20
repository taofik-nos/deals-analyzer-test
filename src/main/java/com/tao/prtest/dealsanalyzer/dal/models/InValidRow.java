package com.tao.prtest.dealsanalyzer.dal.models;

import javax.persistence.Entity;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class InValidRow extends Row{
    public InValidRow(UUID id, String orderingCurrencyCode, String toCurrencyCode, ZonedDateTime timestamp, long dealAmount, String sourceFileName) {
        super(id, orderingCurrencyCode, toCurrencyCode, timestamp, dealAmount, sourceFileName);
    }
}
