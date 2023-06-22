package com.wncsl.core.adapters.mappers.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserActionDTO {

    private String service;
    private String action;
    private String objectName;
    private String objectUuid;
    private String objectValue;
    private String userUuid;
    private LocalDateTime actionAt;
}