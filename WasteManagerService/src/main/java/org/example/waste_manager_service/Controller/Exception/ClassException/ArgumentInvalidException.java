package org.example.waste_manager_service.Controller.Exception.ClassException;

import org.example.waste_manager_service.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class ArgumentInvalidException extends CentralException {
    public ArgumentInvalidException(String text, String value) {
        super("Existence of one or many arguments invalid -> ["+text+"]",
                "C-102",
                "InvalidArgument",
                value,
                HttpStatus.BAD_REQUEST);
    }
}
