package com.wncsl.auth.domain.user;

import javax.persistence.*;

import com.wncsl.auth.domain.permission.Permission;
import com.wncsl.grpc.code.UserGrpc;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID uuid;
    private String username;
    private String password;
    private String type;

    @ManyToMany(fetch = FetchType.LAZY) // ManyToMany, cria uma tabela de relacionamento entre os dois objetos
    @JoinTable(name = "users_permissions", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_permission"))
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return uuid.equals(user.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
