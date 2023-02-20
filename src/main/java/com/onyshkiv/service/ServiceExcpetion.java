package com.onyshkiv.service;

public class ServiceExcpetion extends  Exception{

    public ServiceExcpetion() {
    }
    public ServiceExcpetion(String message) {
        super(message);
    }
    public ServiceExcpetion(String message, Throwable cause) {
        super(message, cause);
    }
    public ServiceExcpetion(Throwable cause) {
        super(cause);
    }
}
