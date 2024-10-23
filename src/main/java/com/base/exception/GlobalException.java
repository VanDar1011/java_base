package com.base.exception;

import com.base.entity.ResponseObject;
import com.base.utils.ResponseStatus;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException ex) {
        ResponseObject responseObjectError = new ResponseObject(ResponseStatus.FAIL.getStatus(),
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObjectError);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseObject> handleConflictException(ConflictException ex) {
        ResponseObject responseObjectError = new ResponseObject(ResponseStatus.FAIL.getStatus(),
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObjectError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseObject> handleConflictException(DataIntegrityViolationException ex) {
        ResponseObject responseObjectError = new ResponseObject(ResponseStatus.FAIL.getStatus(), "Cannot delete because it is referenced by other entities", null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseObjectError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception ex) {
        ResponseObject responseObjectError = new ResponseObject("error", "An error occurred",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObjectError);
    }
}
