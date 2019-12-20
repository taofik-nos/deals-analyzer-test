package com.tao.prtest.dealsanalyzer.service;

import com.tao.prtest.dealsanalyzer.dal.AnalysisSummaryRepository;
import com.tao.prtest.dealsanalyzer.dal.PerCurrencyResultsRepository;
import com.tao.prtest.dealsanalyzer.dal.ValidRowsRepository;
import com.tao.prtest.dealsanalyzer.dal.models.AnalysisSummary;
import com.tao.prtest.dealsanalyzer.dal.models.PerCurrencyResult;
import com.tao.prtest.dealsanalyzer.dal.models.ValidRow;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalysisService {

    private final FileService fileService;
    private final ValidRowsRepository validRowsRepository;
    private final PerCurrencyResultsRepository perCurrencyResultsRepository;
    private final AnalysisSummaryRepository analysisSummaryRepository;
    private final ValidationService validationService;

    private SessionFactory hibernateFactory;

    @Autowired
    public AnalysisService(FileService fileService,
                           ValidRowsRepository validRowsRepository,
                           PerCurrencyResultsRepository perCurrencyResultsRepository,
                           AnalysisSummaryRepository analysisSummaryRepository,
                           ValidationService validationService,
                           EntityManagerFactory factory) {
        this.fileService = fileService;
        this.validRowsRepository = validRowsRepository;
        this.perCurrencyResultsRepository = perCurrencyResultsRepository;
        this.analysisSummaryRepository = analysisSummaryRepository;
        this.validationService = validationService;

        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
    }

    public AnalysisSummary getAnalysisSummary(String fileName) {
        return analysisSummaryRepository.findByFileName(fileName);
    }

    private Map<String, Integer> findDealCountPerOrderingCurrency(List<ValidRow> validRows) {
        Map<String, Integer> dealCountPerOrderingCurrency = new HashMap<>();

        validRows.stream().forEach(row -> {
            try {
                int currentCountPerOrderingCurrency = dealCountPerOrderingCurrency.get(row.orderingCurrencyCode);
                dealCountPerOrderingCurrency.put(row.orderingCurrencyCode, currentCountPerOrderingCurrency + 1);
            } catch (NullPointerException e) {
                dealCountPerOrderingCurrency.put(row.orderingCurrencyCode, 1);
            }
        });

        return dealCountPerOrderingCurrency;
    }

    public AnalysisSummary analyze(MultipartFile file) {
        long startTime = System.currentTimeMillis();

        //read file
        List<String> fileContents = fileService.readFileToArray(file);

        //parse and validate rows
        ParsingResult parsedRows = validationService.parseAndValidateStringRows(fileContents, file.getOriginalFilename());

        //find deal count per currency
        Map<String, Integer> newDealCountPerOrderingCurrency = findDealCountPerOrderingCurrency(parsedRows.validRows);

        //get all the previous per currency results for faster lookup
        List<PerCurrencyResult> allCurrentResults = perCurrencyResultsRepository.findAll();

        //start a single session and transaction for all inserts, this is to improve DB write performance
        StatelessSession session = hibernateFactory.openStatelessSession();
        Transaction tx = session.beginTransaction();

        newDealCountPerOrderingCurrency.forEach((key, val) -> {
            //check if we already have record of the given currency
            Optional<PerCurrencyResult> perCurrencyResult = allCurrentResults.stream().filter(result -> result.orderingCurrencyISOCode.equals(key)).findFirst();

            if (perCurrencyResult.isPresent()) {

                PerCurrencyResult currencyResult = perCurrencyResult.get();
                currencyResult.countOfDeals += val;

                session.insert(currencyResult);
            } else {
                PerCurrencyResult newCurrencyResult = new PerCurrencyResult();
                newCurrencyResult.orderingCurrencyISOCode = key;
                newCurrencyResult.countOfDeals = val;

                session.insert(newCurrencyResult);

            }
        });

        try {
            //add all the valid rows to DB
            parsedRows.validRows.forEach(obj -> {
                session.insert(obj);
            });

            //add all the invalid rows to DB
            parsedRows.inValidRows.forEach(obj -> {
                session.insert(obj);
            });
        } catch (ConstraintViolationException e){
            System.err.println(e);
        }

        tx.commit();
        session.close();

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;

        AnalysisSummary analysisSummary = new AnalysisSummary();
        analysisSummary.executionTimeInMilis = elapsedTime;
        analysisSummary.validDealsCount = parsedRows.validRows.size();
        analysisSummary.invalidDealsCount = parsedRows.inValidRows.size();
        analysisSummary.fileName = file.getOriginalFilename();
        analysisSummary = analysisSummaryRepository.save(analysisSummary);
        return analysisSummary;
    }
}
