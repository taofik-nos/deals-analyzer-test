package com.tao.prtest.dealsanalyzer.service;

import com.tao.prtest.dealsanalyzer.dal.models.InValidRow;
import com.tao.prtest.dealsanalyzer.dal.models.Row;
import com.tao.prtest.dealsanalyzer.dal.models.ValidRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.*;

final class ParsingResult{
    public List<ValidRow> validRows;
    public List<InValidRow> inValidRows;
}

@Service
public class FileService {
    public List<String> readFileToArray(MultipartFile file){

        BufferedReader br;
        List<String> result = new ArrayList<>();
        try {

            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result.add(line);
            }

            return result;

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
