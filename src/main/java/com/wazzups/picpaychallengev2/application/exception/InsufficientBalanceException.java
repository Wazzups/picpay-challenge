package com.wazzups.picpaychallengev2.application.exception;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
