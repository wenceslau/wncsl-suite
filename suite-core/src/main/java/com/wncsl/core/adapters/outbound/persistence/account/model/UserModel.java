package com.wncsl.core.adapters.outbound.persistence.account.model;

import com.wncsl.core.adapters.outbound.persistence.ListenerModel;
import com.wncsl.core.adapters.outbound.persistence.Model;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@EntityListeners(ListenerModel.class)
public class UserModel extends Model {

    private String name;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY) // ManyToMany, cria uma tabela de relacionamento entre os dois objetos
    @JoinTable(name = "users_permissions", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    private Set<PermissionModel> permissions;

    public Set<PermissionModel> getPermissions() {
        if (permissions == null)
            permissions = new HashSet<>();
        return permissions;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

}
