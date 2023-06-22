package com.wncsl.audit;

import com.wncsl.audit.consumer.grpc.GrpcAuditServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
public class AuditListener {

    @Value("${grpc.server.port}")
    private int port;

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println(LocalDateTime.now() + " AuditListener.applicationReadyEvent() - INIT");

        new Thread(() -> { // Lambda Expression
            try {
                GrpcAuditServer auditServer = new GrpcAuditServer();
                auditServer.start(port);
                auditServer.blockUntilShutdown();
            } catch (Exception e) {
                System.err.println(e);
            }

        }).start();

        System.out.println(LocalDateTime.now() + " AuditListener.applicationReadyEvent() - END");

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
