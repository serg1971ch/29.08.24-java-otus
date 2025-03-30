package ru.otus.dataJdbc.entities;

public enum Genre {
    FANTASY("Фэнтези"), SCIFI("Научная фантастика");

    private String name;

    public String getName() {
        return name;
    }

    Genre(String name) {
        this.name = name;
    }
}
