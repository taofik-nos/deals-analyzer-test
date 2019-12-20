package com.tao.prtest.dealsanalyzer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    @Test
    void parseAndValidateStringRows() {
        List<String> row = new ArrayList<>();

        row.add("id,orderingCurrencyCode,toCurrencyCode,timestamp,dealAmount");
        row.add("bee3f976-7ade-4c5f-95d4-d516c1125da9,, BBD, 1994-06-16T05:40+03:00[Asia/Riyadh], 98906");

        ParsingResult result = validationService.parseAndValidateStringRows(row, "anyname");

        assertEquals(result.inValidRows.size(), 1);
        assertEquals(result.validRows.size(), 0);
    }


}