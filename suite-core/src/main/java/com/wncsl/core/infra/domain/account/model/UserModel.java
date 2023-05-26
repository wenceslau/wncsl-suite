package com.wncsl.core.infra.domain.account.model;

import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID uuid;
    private String name;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY) // ManyToMany, cria uma tabela de relacionamento entre os dois objetos
    @JoinTable(name = "users_permissions", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    private Set<PermissionModel> permissions = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        return id.equals(userModel.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

//    public UserDTO toDTO(){
//        return UserDTO.builder()
//                .uuid(uuid)
//                .name(name)
//                .username(username)
//                .password(password)
//                .build();
//    }

    public User toEntity(){
        List<Permission> permissionList = permissions.stream().map(p -> p.toEntity()).collect(Collectors.toList());
        User user = new User(uuid, name, username ,permissionList);
        user.createPassword(password);
        return user;
    }
}
