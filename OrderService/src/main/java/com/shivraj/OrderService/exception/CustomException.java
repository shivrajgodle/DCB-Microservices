package com.shivraj.OrderService.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{

    private String errorCode;
    private int status;

    public CustomException(String message, int status, String errorCode){
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
