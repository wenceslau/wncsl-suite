package com.wncsl.core.adapters.mappers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.wncsl.core.adapters.mappers.dto.View.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @JsonView({Full.class, Resume.class })
    private UUID uuid;

    @JsonView({Full.class, Resume.class, Insert.class, Update.class })
    private String name;

    @JsonView({Full.class, Insert.class})
    private String username;

    @JsonView({Insert.class})
    private String password;

    @JsonView({Full.class, Resume.class, Insert.class, Update.class})
    private List<PermissionDTO> permissions;

    public UserDTO() {
        permissions = new ArrayList<>();
    }
}
