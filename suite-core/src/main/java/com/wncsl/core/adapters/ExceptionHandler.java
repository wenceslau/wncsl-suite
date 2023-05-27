package com.wncsl.core.adapters;

import com.wncsl.core.domain.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({ BusinessException.class })
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {

        Error err = getError(HttpStatus.BAD_REQUEST, ex, (ServletWebRequest) request);

        return handleExceptionInternal(ex, err, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        Error err = getError(HttpStatus.BAD_GATEWAY, ex, (ServletWebRequest) request);

        return handleExceptionInternal(ex, err, new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handlerException(Exception ex, WebRequest request) {

        Error err = getError(HttpStatus.INTERNAL_SERVER_ERROR, ex, (ServletWebRequest) request);

        return handleExceptionInternal(ex, err, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);

    }

    private Error getError(HttpStatus httpStatus, Exception ex, ServletWebRequest request) {
        String status = String.valueOf(httpStatus.value());
        String error = httpStatus.getReasonPhrase();
        String message = ex.getMessage();
        String exception = ex.getClass().getSimpleName();
        String lineError = ex.getStackTrace()[0].getFileName()+":"+ex.getStackTrace()[0].getLineNumber();
        String path = request.getRequest().getRequestURI();

        ex.printStackTrace();
        return new Error(status, error, message, exception, lineError, path);
    }

    static class Error {

        private final String status, error, message, exception, detail, path;

        public Error(String status, String error, String message, String exception, String detail, String path) {
            this.status = status;
            this.error = error;
            this.message = message;
            this.exception = exception;
            this.detail = detail;
            this.path = path;
        }

        public String getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public String getException() {
            return exception;
        }

        public String getDetail() {
            return detail;
        }

        public String getPath() {
            return path;
        }
    }

}