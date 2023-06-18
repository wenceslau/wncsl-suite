package com.wncsl.audit.domain.useraaction;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "user_action")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String service;
    private String action;
    private String objectName;
    private String objectUuid;
    private String objectValue;
    @Column(columnDefinition = "uuid")
    private UUID userUuid;
    private LocalDateTime actionAt;
}