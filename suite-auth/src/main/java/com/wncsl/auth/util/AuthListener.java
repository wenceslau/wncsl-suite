package com.wncsl.auth.util;

import com.wncsl.auth.domain.permission.Permission;
import com.wncsl.auth.domain.permission.PermissionService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthListener {


    private final Initializer initializer;

    public AuthListener(Initializer initializer) {
        this.initializer = initializer;
    }

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println(LocalDateTime.now() + " AccountMainInitialize.applicationReadyEvent() - INIT");

        //initializer.init();

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
