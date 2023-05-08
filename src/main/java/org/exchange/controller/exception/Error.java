package org.exchange.controller.exception;

import lombok.Data;

@Data
public class Error {

    private String message;
    private String type;
    private String code;
}
