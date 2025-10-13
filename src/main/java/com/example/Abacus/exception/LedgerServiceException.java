package com.example.Abacus.exception;

public class LedgerServiceException extends RuntimeException {
    public LedgerServiceException(String message) {
        super(message);
    }
}