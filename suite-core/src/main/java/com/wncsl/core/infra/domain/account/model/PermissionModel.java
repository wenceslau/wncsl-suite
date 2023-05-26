package com.wncsl.core.infra.domain.account.model;

import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.presentation.account.dto.PermissionDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Permissions")
public class PermissionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID uuid;
    private String role;
    private String description;

    @Override
    public String toString() {
        return "Permission{" +
                "role='" + role + '\'' +
                '}';
    }

    public Permission toEntity(){
        return Permission.fromModel(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionModel accountModel = (PermissionModel) o;

        return id.equals(accountModel.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public PermissionDTO toDTO(){
        return PermissionDTO.builder()
                .uuid(uuid)
                .role(role)
                .description(description)
                .build();
    }
}
