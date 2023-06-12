package com.API.VideosAPI.errors;

public class ValidationError {


    private String code;
    private String message;


    public String getCode() {return this.code;}
    public void setCode(String code) {this.code = code;}

    public String getMessage() {return this.message;}
    public void setMessage(String message) {this.message = message;}
}
