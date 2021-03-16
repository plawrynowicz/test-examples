package com.github.plawrynowicz.exampleproject.backend.dao;

import com.github.plawrynowicz.exampleproject.backend.entity.ActionTypeEntity;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ActionTypeDao {


    @SqlUpdate("INSERT INTO StrategyProcess.dbo.ActionType (id, symbol) VALUES (:id, :symbol)")
    void save(@BindBean ActionTypeEntity actionTypeEntity);
}
