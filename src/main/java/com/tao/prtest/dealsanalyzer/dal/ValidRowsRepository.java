package com.tao.prtest.dealsanalyzer.dal;

import com.tao.prtest.dealsanalyzer.dal.models.Row;
import com.tao.prtest.dealsanalyzer.dal.models.ValidRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ValidRowsRepository extends CrudRepository<ValidRow, UUID> {
}
