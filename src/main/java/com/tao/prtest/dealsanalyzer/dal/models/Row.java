package com.tao.prtest.dealsanalyzer.dal.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@MappedSuperclass
public abstract class Row {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    public UUID id;
    public String orderingCurrencyCode;
    public String toCurrencyCode;
    public ZonedDateTime timestamp;
    public long dealAmount;
    public String sourceFileName;

    public Row(UUID id, String orderingCurrencyCode, String toCurrencyCode, ZonedDateTime timestamp, long dealAmount, String sourceFileName) {
        this.id = id;
        this.orderingCurrencyCode = orderingCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.timestamp = timestamp;
        this.dealAmount = dealAmount;
        this.sourceFileName = sourceFileName;
    }

    public Row() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOrderingCurrencyCode() {
        return orderingCurrencyCode;
    }

    public void setOrderingCurrencyCode(String orderingCurrencyCode) {
        this.orderingCurrencyCode = orderingCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(long dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    @Override
    public String toString() {
        return "DealEntry{" +
                "id=" + id +
                ", orderingCurrencyCode='" + orderingCurrencyCode + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                ", timestamp=" + timestamp.format(DateTimeFormatter.ISO_DATE_TIME) +
                ", dealAmount=" + dealAmount +
                ", sourceFileName=" + sourceFileName +
                '}';
    }
}
