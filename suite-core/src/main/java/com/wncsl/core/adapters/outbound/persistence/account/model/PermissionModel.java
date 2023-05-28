package com.wncsl.core.adapters.outbound.persistence.account.model;

import com.wncsl.core.adapters.outbound.persistence.ListenerModel;
import com.wncsl.core.adapters.outbound.persistence.Model;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Permissions")
@EntityListeners(ListenerModel.class)
public class PermissionModel extends Model {

    private String role;
    private String description;

    @Override
    public String toString() {
        return "Permission{" +
                "role='" + role + '\'' +
                '}';
    }

}
