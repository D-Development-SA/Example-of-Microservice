package org.example.waste_manager_service.Controller.Exception.ClassException;


import org.example.waste_manager_service.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.example.waste_manager_service.Entity.WasteManagerEntity;
import org.springframework.http.HttpStatus;

public class IncorrectFieldException extends CentralException {
    public IncorrectFieldException(String text, String value) {
        super("Existence of a field with characters not allowed -> ["+text+"]",
                "C-101",
                "ArgumentsInvalid",
                value,
                HttpStatus.BAD_REQUEST);
    }
    public IncorrectFieldException(String text, WasteManagerEntity value) {
        super("Existence of a field with characters not allowed -> ["+text+"], \n" +
                        "if content specials characters you can solicit a exclusion",
                "C-101",
                "ArgumentsInvalid",
                value.getName(),
                HttpStatus.BAD_REQUEST);
    }
}
