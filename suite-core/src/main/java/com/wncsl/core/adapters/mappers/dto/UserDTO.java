package com.wncsl.core.adapters.mappers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID uuid;
    private String name;
    private String username;
    @JsonIgnoreProperties(allowSetters = true)
    private String password;
    private List<PermissionDTO> permissions;

    public UserDTO() {
        permissions = new ArrayList<>();
    }
}
