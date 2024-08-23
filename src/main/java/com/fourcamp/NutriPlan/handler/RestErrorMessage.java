package com.fourcamp.NutriPlan.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RestErrorMessage {

    private HttpStatus status;
    private String message;
}
