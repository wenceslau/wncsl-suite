package com.wncsl.auth.domain.logonhistory;

import com.wncsl.auth.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "logon_history")
public class LogonHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(columnDefinition = "uuid")
    private UUID userUuid;
    private LocalDateTime dateLogon;
    private String status;
    private String cause;
    private String invalidToken;
    private String device;
    private String browser;
    private String address;
    private LocalDateTime created;
}