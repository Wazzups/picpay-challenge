package com.wazzups.picpaychallengev2.application.exception;

public class WalletNotFoundException extends RuntimeException{
    public WalletNotFoundException(String message) {
        super(message);
    }
}
