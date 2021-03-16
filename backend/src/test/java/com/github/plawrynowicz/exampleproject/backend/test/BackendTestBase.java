package com.github.plawrynowicz.exampleproject.backend.test;

import com.github.plawrynowicz.exampleproject.backend.dao.ActionTypeDao;
import com.github.plawrynowicz.exampleproject.backend.dao.Dao;
import com.github.plawrynowicz.exampleproject.backend.dao.DocumentDao;
import com.github.plawrynowicz.exampleproject.backend.dao.StrategyDroolsResultDao;
import com.github.plawrynowicz.exampleproject.backend.dto.StatusDto;
import com.github.plawrynowicz.exampleproject.backend.entity.ActionTypeEntity;
import com.github.plawrynowicz.exampleproject.backend.entity.DocumentEntity;
import com.github.plawrynowicz.exampleproject.backend.entity.StrategyDroolsResultEntity;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BackendTestBase {

    @SuppressWarnings("rawtypes")
    @Container
    private final JdbcDatabaseContainer mssqlServerContainer = new MSSQLServerContainer(MSSQLServerContainer.IMAGE)
            .acceptLicense()
            .withInitScript("tables.sql");

    protected Dao dao;
    protected WireMockServer wiremock;

    private Handle jdbiHandle;

    @BeforeEach
    public void setup() {
        wiremock = new WireMockServer();
        wiremock.start();

        Jdbi jdbi = Jdbi.create(mssqlServerContainer.getJdbcUrl(),
                mssqlServerContainer.getUsername(),
                mssqlServerContainer.getPassword());

        jdbi.registerRowMapper(ConstructorMapper.factory(ActionTypeEntity.class));
        jdbi.registerRowMapper(ConstructorMapper.factory(DocumentEntity.class));
        jdbi.registerRowMapper(ConstructorMapper.factory(StrategyDroolsResultEntity.class));
        jdbi.registerRowMapper(ConstructorMapper.factory(StatusDto.class));

        jdbi.installPlugin(new SqlObjectPlugin());
        jdbiHandle = jdbi.open();

        dao = new Dao(
                jdbiHandle.attach(ActionTypeDao.class),
                jdbiHandle.attach(DocumentDao.class),
                jdbiHandle.attach(StrategyDroolsResultDao.class)
        );
    }

    @AfterEach
    public void cleanup() {
        wiremock.stop();
        jdbiHandle.close();
    }
}
