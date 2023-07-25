package com.spring.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountException extends RuntimeException {

    public AccountException(String message) {
        super(message);
    }

    public static class AccountAlreadyExistsException extends AccountException {
        public AccountAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidCreditException extends AccountException {
        public InvalidCreditException(String message) {
            super(message);
        }
    }
    public static class AccountNotFoundException extends AccountException {
        public AccountNotFoundException(String message) {
            super(message);
        }
    }

    public static class AccountCreationFailedException extends AccountException {
        public AccountCreationFailedException(String message) {
            super(message);
        }
    }
}
