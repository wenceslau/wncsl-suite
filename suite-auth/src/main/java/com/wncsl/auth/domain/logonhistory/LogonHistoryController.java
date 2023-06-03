package com.wncsl.auth.domain.logonhistory;

import com.wncsl.auth.domain.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/histories")
public class LogonHistoryController {

    LogonHistoryService logonHistoryService;

    public LogonHistoryController(LogonHistoryService logonHistoryService) {
        this.logonHistoryService = logonHistoryService;
    }

    @GetMapping()
    public ResponseEntity<Object> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(logonHistoryService.listAll());
    }
}
