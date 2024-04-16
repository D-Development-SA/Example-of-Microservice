package org.example.waste_manager_service.Controller.Exception.GeneralExceptionAndControllerAdvice;

import org.springframework.http.HttpStatus;

public interface ICentralException {
    String getCode();
    String getType();
    String getMessage();
    HttpStatus getHttpStatus();
    String getValueField();
}
