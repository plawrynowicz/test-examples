package com.github.plawrynowicz.exampleproject.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StrategyDroolsResultEntity {

    private Long id;
    private String actionTypeId;
    private String status;
    private Long segmentationId;

}
