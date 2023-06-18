package com.wncsl.audit.consumer.grpc;

import com.wncsl.audit.domain.useraaction.UserActionMapper;
import com.wncsl.audit.domain.useraaction.UserActionService;
import com.wncsl.grpc.code.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class GrpcServerService extends AuditServiceGrpc.AuditServiceImplBase {

    private final UserActionService userActionService;

    public GrpcServerService(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    private static final Logger log = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    public void addUserAction(UserActionGrpc request, StreamObserver<Response> responseObserver) {

        String message;
        STATUS status = STATUS.CREATED;
        log.info(">>>>: Received "+request.getClass().getSimpleName()+" object name... "+request.getObjectName());

        try {
            userActionService.create(UserActionMapper.build(request));
            message = "Action successfully";
        }catch (Exception ex){
            status = STATUS.ERROR;
            message = ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        Response response = Response.newBuilder()
                .setStatus(status)
                .setMessage(message)
                .setObject(request.getClass().getSimpleName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info(">>>>: Status... " + response);
    }
}