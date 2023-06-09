package com.wncsl.core.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CoreListener {

    private Initializer initializer;

    public CoreListener(Initializer initializer) {
        this.initializer = initializer;
    }

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationReadyEvent() - INIT");

        initializer.init();

        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationReadyEvent() - END");

    }

    @EventListener
    private void applicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationStartedEvent()");
    }

    @EventListener
    private void applicationStartingEvent(ApplicationStartingEvent event) {
        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationStartingEvent()");
    }

}
