package ru.otus.javaprodi.application.services;

import ru.otus.javaprodi.application.model.Equation;

import java.util.List;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
