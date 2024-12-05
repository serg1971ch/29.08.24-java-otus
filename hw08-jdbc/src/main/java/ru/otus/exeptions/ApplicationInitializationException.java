package ru.otus.exeptions;

public class ApplicationInitializationException extends RuntimeException {
    public ApplicationInitializationException(String message) {
        super(message);
    }
}