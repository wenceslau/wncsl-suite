package com.wncsl.audit.consumer.grpc;

import com.wncsl.audit.domain.useraaction.UserActionMapper;
import com.wncsl.audit.domain.useraaction.UserActionService;
import com.wncsl.grpc.audit.AuditServiceGrpc;
import com.wncsl.grpc.audit.Response;
import com.wncsl.grpc.audit.STATUS;
import com.wncsl.grpc.audit.UserActionGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrpcAuditService extends AuditServiceGrpc.AuditServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(GrpcAuditService.class);
    private static UserActionService userActionService;

    @Autowired
    public void init(UserActionService userActionService) {
        GrpcAuditService.userActionService = userActionService;
        log.info("Initializing GrpcAuditService with dependency UserActionService");
    }

    @Override
    public void addUserAction(UserActionGrpc request, StreamObserver<Response> responseObserver) {
        String message;
        STATUS status = STATUS.SUCCESS;
        log.info(">>>>: Received "+request.getClass().getSimpleName()+" object name... "+request.getObjectName());

        try {
            userActionService.create(UserActionMapper.build(request));
            message = "Action successfully";
        }catch (Exception ex){
            status = STATUS.FAILURE;
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