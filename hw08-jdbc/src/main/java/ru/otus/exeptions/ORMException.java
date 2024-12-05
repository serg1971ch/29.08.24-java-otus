package ru.otus.exeptions;

public class ORMException extends RuntimeException {
    public ORMException(String message) {
        super(message);
    }
}
