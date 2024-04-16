package org.example.wastemanageraddressservice.Controller.Exception.BDExcepcion;

import org.example.wastemanageraddressservice.Controller.Exception.GeneralExceptionAndControllerAdvice.CentralException;
import org.springframework.http.HttpStatus;

public class AnomalyFoundBDException extends CentralException {
    public AnomalyFoundBDException(String valueField) {
        super("Anomaly detected in the BD",
                "BD-400",
                "AnomalyBD",
                valueField,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
