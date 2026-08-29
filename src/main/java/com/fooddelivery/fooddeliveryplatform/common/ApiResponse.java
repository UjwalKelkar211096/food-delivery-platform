package com.fooddelivery.fooddeliveryplatform.common;

import java.time.LocalDateTime;

public class ApiResponse <T>{

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timeStamp;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

    public boolean isSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public T getData(){
        return data;
    }

    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }
}
