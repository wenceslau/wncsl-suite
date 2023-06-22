package com.wncsl.core.adapters.outbound.grpc;

import com.wncsl.core.adapters.mappers.UserActionMapper;
import com.wncsl.core.adapters.mappers.dto.UserActionDTO;

import com.wncsl.grpc.audit.AuditServiceGrpc.AuditServiceBlockingStub;
import com.wncsl.grpc.audit.Response;
import com.wncsl.grpc.audit.STATUS;
import com.wncsl.grpc.audit.UserActionGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcAuditClientService {

    private static final Logger log = LoggerFactory.getLogger(GrpcAuditClientService.class);

    @GrpcClient("audit-grpc-server")
    private AuditServiceBlockingStub auditBlockingStub;

    public String addUserAction(final UserActionDTO userActionDTO) {
        try {
            UserActionGrpc request = UserActionMapper.toGrpc(userActionDTO);
            final Response response = this.auditBlockingStub.addUserAction(request);
            if (STATUS.ERROR.equals(response.getStatus())){
                log.error(response.getMessage());
            }
            return response.getStatus().name();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            //addUserAction(userActionDTO);
            return error;
        }
    }

}