package com.github.plawrynowicz.exampleproject.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateStrategyDroolsResultPostDto {

    private List<Long> clientIds;
    private Long actionDefinitionId;
    private Long segmentationId;

}
