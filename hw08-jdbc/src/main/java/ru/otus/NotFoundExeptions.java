package ru.otus;

public class NotFoundExeptions extends RuntimeException {
    public NotFoundExeptions() {
        super("Not found file");
    }
}
