package com.wncsl.core.adapters.outbound.grpc;

import com.wncsl.grpc.code.*;
import com.wncsl.grpc.code.AuditServiceGrpc.AuditServiceBlockingStub;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcAuditClientService {

    private static final Logger log = LoggerFactory.getLogger(GrpcAuditClientService.class);

    @GrpcClient("auth-grpc-server")
    private AuditServiceBlockingStub auditBlockingStub;

    public String addUserAction(final String value, ACTION action) {
//        try {
//            UserGrpc request = UserMapper.toGrpc(user, action);
//            final Response response = this.auditBlockingStub.addUser(request);
//            if (STATUS.ERROR.equals(response.getStatus())){
//                log.error(response.getMessage());
//            }
//            return response.getStatus().name();
//        } catch (final StatusRuntimeException e) {
//            String error = "FAILED with " + e;
//            log.error(error);
//            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
//            addUser(user,action);
//            return error;
//        }
        return "";
    }

}