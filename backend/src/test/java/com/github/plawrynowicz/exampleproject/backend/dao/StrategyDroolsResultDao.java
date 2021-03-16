package com.github.plawrynowicz.exampleproject.backend.dao;

import com.github.plawrynowicz.exampleproject.backend.dto.StatusDto;
import com.github.plawrynowicz.exampleproject.backend.entity.StrategyDroolsResultEntity;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface StrategyDroolsResultDao {

    @SqlQuery("""
            SELECT sdr.status
            FROM StrategyProcess.dbo.StrategyDroolsResult sdr (NOLOCK)
            JOIN StrategyProcess.dbo.ActionType at (NOLOCK) ON at.id = sdr.actionTypeId
            WHERE sdr.status = :status
              AND sdr.segmentationId = :segmentationId
              AND at.symbol = :symbol
            """)
    List<String> findStatuses(@Bind("status") String status,
                              @Bind("segmentationId") int segmentationId,
                              @Bind("symbol") String symbol);


    @SqlQuery("""
            SELECT sdr.status statusFromStr, d.status statusFromDocument
            FROM StrategyProcess.dbo.StrategyDroolsResult sdr (NOLOCK)
            JOIN StrategyProcess.dbo.ActionType at (NOLOCK) ON at.id = sdr.actionTypeId
            JOIN StrategyProcess.dbo.Document d (NOLOCK) ON d.strategyRecordId = sdr.id
            WHERE sdr.status = :status
              AND sdr.segmentationId = :segmentationId
              AND at.symbol = :symbol 
            """)
    List<StatusDto> findStatusesFromStrategyDroolsResultAndDocument(String status,
                                                                    int segmentationId,
                                                                    String symbol);


    @SqlUpdate("INSERT INTO StrategyProcess.dbo.StrategyDroolsResult (id, actionTypeId, status, segmentationId ) VALUES (:id, :actionTypeId, :status, :segmentationId)")
    void save(@BindBean StrategyDroolsResultEntity strategyDroolsResultEntity);
//    void insertNamed(@Bind("clientIds") Long clientIds, @Bind("actionDefinitionId") Long actionDefinitionId, @Bind("segmentionId") Long segmentationId);


}
