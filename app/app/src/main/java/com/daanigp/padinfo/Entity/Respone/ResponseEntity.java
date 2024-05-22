package com.daanigp.padinfo.Entity.Respone;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {
    private int code;
    private String messege;
    private Map<String, String> errors;

    public ResponseEntity() {
    }

    private ResponseEntity(int errorCode, String errorMessage)
    {
        this.code = errorCode;
        this.messege = errorMessage;
        this.errors = new HashMap<>();
    }

    private ResponseEntity(int errorCode, String errorMessage, Map<String, String> errors)
    {
        this.code = errorCode;
        this.messege = errorMessage;
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
