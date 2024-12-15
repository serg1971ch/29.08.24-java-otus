package ru.otus.exeptions;

public class NotFoundExeptions extends RuntimeException {
    public NotFoundExeptions(String s) {
        super("Not found product");
    }
}
