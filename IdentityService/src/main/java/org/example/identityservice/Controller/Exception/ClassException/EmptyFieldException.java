package org.example.identityservice.Controller.Exception.ClassException;

import lombok.Getter;
import org.example.identityservice.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyFieldException extends CentralException {
    public EmptyFieldException(String text) {
        super("Field interpreted as empty or null -> ["+text+"]",
                "C-100",
                "NullField",
                text,
                HttpStatus.BAD_REQUEST);
    }
}
