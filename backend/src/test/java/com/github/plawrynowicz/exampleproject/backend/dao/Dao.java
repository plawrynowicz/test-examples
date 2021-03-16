package com.github.plawrynowicz.exampleproject.backend.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Dao {

    private final ActionTypeDao actionTypeDao;
    private final DocumentDao documentDao;
    private final StrategyDroolsResultDao strategyDroolsResultDao;
}
