package com.tao.prtest.dealsanalyzer.dal;

import com.tao.prtest.dealsanalyzer.dal.models.AnalysisSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AnalysisSummaryRepository extends CrudRepository<AnalysisSummary, Long> {

    @Query("select u from AnalysisSummary u where u.fileName LIKE ?1")
    AnalysisSummary findByFileName(String fileName);
}
