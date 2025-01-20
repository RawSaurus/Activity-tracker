package com.miroslav.acitivity_tracker.exception;

public class ActionNotAllowed extends RuntimeException{

    public ActionNotAllowed(String msg){
        super(msg);
    }
}
