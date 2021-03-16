package com.github.plawrynowicz.exampleproject.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusDto {

    private String statusFromStr;
    private String statusFromDocument;

}
