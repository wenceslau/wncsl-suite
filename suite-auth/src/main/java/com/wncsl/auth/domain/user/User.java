package com.wncsl.auth.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.wncsl.grpc.code.UserGrpc;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Users")
public class User {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID uuid;
    private String username;
    private String password;
    private String type;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    public static User build(UserGrpc userGrpc){
        return User.builder()
                .uuid(UUID.fromString(userGrpc.getUuid()))
                .username(userGrpc.getUsername())
                .password(userGrpc.getPassword())
                .type("APP")
                .build();
    }

    public static UserGrpc build(User user, String status){
        return UserGrpc.newBuilder()
                .setUuid(user.getUuid().toString())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setStatus(status)
                .build();
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
