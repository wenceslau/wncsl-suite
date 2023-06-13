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


    @JsonView({View.Full.class, View.Resume.class, View.Filter.class })
    private UUID uuid;

    @JsonView({View.Full.class, View.Insert.class, View.Filter.class })
    private String role;

    @JsonView({View.Full.class, View.Resume.class, View.Insert.class, View.Update.class, View.Filter.class })
    private String description;

}
