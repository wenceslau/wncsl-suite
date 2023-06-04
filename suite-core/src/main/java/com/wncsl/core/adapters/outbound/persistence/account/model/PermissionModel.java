package com.wncsl.core.adapters.outbound.persistence.account.model;

import com.wncsl.core.adapters.outbound.persistence.ListenerModel;
import com.wncsl.core.adapters.outbound.persistence.Model;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permissions")
public class PermissionModel extends Model {

    private String role;
    private String description;

}
