package org.example.waste_manager_service.Controller.Errors;

import lombok.Builder;
import lombok.Data;
import java.util.HashMap;

@Data
@Builder
public class Errors {
    private String code;
    private String type;
    private String message;
    private String value;
    private HashMap<String, String> errors;
}
