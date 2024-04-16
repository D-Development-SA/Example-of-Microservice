package org.example.wastemanageraddressservice.Controller.Exception.ClassException;


import org.example.wastemanageraddressservice.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntity;
import org.springframework.http.HttpStatus;

public class IncorrectFieldException extends CentralException {
    public IncorrectFieldException(String text, String value) {
        super("Existence of a field with characters not allowed -> ["+text+"]",
                "C-101",
                "ArgumentsInvalid",
                value,
                HttpStatus.BAD_REQUEST);
    }
    public IncorrectFieldException(String text, WasteManagerAddressEntity value) {
        super("Existence of a field with characters not allowed -> ["+text+"], \n" +
                        "if content specials characters you can solicit a exclusion",
                "C-101",
                "ArgumentsInvalid",
                value.getAddress(),
                HttpStatus.BAD_REQUEST);
    }
}
