package com.tao.prtest.dealsanalyzer.dal;

import com.tao.prtest.dealsanalyzer.dal.models.PerCurrencyResult;
import com.tao.prtest.dealsanalyzer.dal.models.ValidRow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PerCurrencyResultsRepository extends CrudRepository<PerCurrencyResult, String> {
    List<PerCurrencyResult> findAll();
}
