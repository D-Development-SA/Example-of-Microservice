package org.example.waste_manager_service.Controller.Exception.BDExcepcion;

import org.example.waste_manager_service.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class AccessBDException extends CentralException {
    public AccessBDException() {
        super("Cannot were perform the query to the BD correctly",
                "BD-301",
                "ErrorQuery",
                "???",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
