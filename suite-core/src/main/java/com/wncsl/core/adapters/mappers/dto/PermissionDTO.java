package com.wncsl.core.adapters.mappers.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PermissionDTO {

    private UUID uuid;

    private String role;

    private String description;

}