package com.tao.prtest.dealsanalyzer.service;

import com.tao.prtest.dealsanalyzer.dal.models.InValidRow;
import com.tao.prtest.dealsanalyzer.dal.models.ValidRow;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ValidationService {

    public ParsingResult parseAndValidateStringRows(List<String> stringRows, String sourceFileName){
        String[] rowTokens;
        List<ValidRow> parsedValidRows = new ArrayList<>();
        List<InValidRow> parsedInValidRows = new ArrayList<>();

        for (int i = 1; i < stringRows.size(); i++) {

            String rowStr = stringRows.get(i);

            rowTokens = rowStr.split(", ");

            UUID id = UUID.randomUUID();
            String fromCurrency = "";
            String toCurrency = "";
            ZonedDateTime timestamp = null;
            long amount = 0;

            boolean invalid = false;

            if(rowTokens.length != 5){
                invalid = true;
            }

            try{
                if(rowTokens[0] == null || rowTokens[0].isEmpty())
                    throw new Exception("Missing value");

                id = UUID.fromString(rowTokens[0]);
            }catch (Exception e){
                invalid = true;
            }
            try{
                if(rowTokens[3] == null || rowTokens[3].isEmpty())
                    throw new Exception("Missing value");

                timestamp = ZonedDateTime.parse(rowTokens[3]);
            }catch (Exception e){
                invalid = true;
            }
            try{
                if(rowTokens[1] == null || rowTokens[1].isEmpty())
                    throw new Exception("Missing value");
                fromCurrency = rowTokens[1];
            }catch (Exception e){
                invalid = true;
            }
            try{
                if(rowTokens[2] == null || rowTokens[2].isEmpty())
                    throw new Exception("Missing value");
                toCurrency = rowTokens[2];
            }catch (Exception e){
                invalid = true;
            }
            try{
                if(rowTokens[4] == null || rowTokens[4].isEmpty())
                    throw new Exception("Missing value");
                amount = Long.parseLong(rowTokens[4]);
            }catch (Exception e){
                invalid = true;
            }

            if(invalid){
                parsedInValidRows.add(new InValidRow(id,fromCurrency,toCurrency, timestamp,amount, sourceFileName));
            }else{
                parsedValidRows.add(new ValidRow(id,fromCurrency,toCurrency, timestamp,amount, sourceFileName));
            }
        }

        ParsingResult result = new ParsingResult();
        result.validRows = parsedValidRows;
        result.inValidRows = parsedInValidRows;

        return result;
    }


}
