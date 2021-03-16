package com.github.plawrynowicz.exampleproject.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentEntity {

    private Long id;
    private Integer strategyRecordId;
    private String status;
}
