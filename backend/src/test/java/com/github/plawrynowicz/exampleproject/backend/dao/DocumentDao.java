package com.github.plawrynowicz.exampleproject.backend.dao;

import com.github.plawrynowicz.exampleproject.backend.entity.DocumentEntity;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface DocumentDao {

    @SqlUpdate("INSERT INTO StrategyProcess.dbo.Document (id, strategyRecordId, status) VALUES (:id, :strategyRecordId, :status)")
    void save(@BindBean DocumentEntity documentEntity);

    @SqlQuery("SELECT count(*) FROM StrategyProcess.dbo.Document WHERE status = :status")
    int countByStatus(String status);

    @SqlQuery("SELECT count(*) FROM StrategyProcess.dbo.Document")
    int count();
}
