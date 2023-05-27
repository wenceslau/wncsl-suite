package com.wncsl.core.adapters.mappers.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PermissionDTO {


    @JsonView({View.Full.class, View.Resume.class })
    private UUID uuid;

    @JsonView({View.Full.class, View.Insert.class })
    private String role;

    @JsonView({View.Full.class, View.Resume.class, View.Insert.class, View.Update.class })
    private String description;

}
