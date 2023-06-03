package com.wncsl.auth.domain.permission;


import com.wncsl.auth.domain.user.User;
import com.wncsl.grpc.code.PermissionGrpc;
import com.wncsl.grpc.code.UserGrpc;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "permissions")
public class Permission {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID uuid;
    private String role;
    private String description;

    @Override
    public String toString() {
        return "Permission{" +
                "role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }



}