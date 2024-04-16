package org.example.wastemanageraddressservice.Controller.Exception.ClassException;

import lombok.Getter;
import org.example.wastemanageraddressservice.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyFieldException extends CentralException {
    public EmptyFieldException(String text, String value) {
        super("Field interpreted as empty or null -> ["+text+"]",
                "C-100",
                "NullField",
                value,
                HttpStatus.BAD_REQUEST);
    }
}
