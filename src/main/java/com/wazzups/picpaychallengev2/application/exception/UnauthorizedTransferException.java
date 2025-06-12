package com.wazzups.picpaychallengev2.application.exception;

public class UnauthorizedTransferException extends RuntimeException {
    public UnauthorizedTransferException(String message) {
        super(message);
    }
}
