package org.example.wastemanageraddressservice.Controller.Exception.GeneralExceptionAndControllerAdvice;

import org.springframework.http.HttpStatus;

public interface ICentralException {
    String getCode();
    String getType();
    String getMessage();
    HttpStatus getHttpStatus();
    String getValueField();
}
