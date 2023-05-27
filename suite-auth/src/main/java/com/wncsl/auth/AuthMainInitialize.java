package com.wncsl.auth;

import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AuthMainInitialize {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println(LocalDateTime.now() + " AccountMainInitialize.applicationReadyEvent() - INIT");

        UUID uuid = UUID.randomUUID();
        System.out.println("UUID: " + uuid);

        User user = User.builder()
                .uuid(uuid)
                .username("api")
                .password(passwordEncoder.encode("api123"))
                .type("GRPC")
                .build();

        user =  userService.create(user);

        System.out.println("User created: " + user.getUuid());

        System.out.println(LocalDateTime.now() + " CoreAppListener.applicationReadyEvent() - END");

    }

    @EventListener
    private void applicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println(LocalDateTime.now() + " CoreAppListener.applicationStartedEvent()");
    }

    @EventListener
    private void applicationStartingEvent(ApplicationStartingEvent event) {
        System.out.println(LocalDateTime.now() + " CoreAppListener.applicationStartingEvent()");
    }

}
