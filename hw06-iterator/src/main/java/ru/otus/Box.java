package ru.otus;

import java.util.Iterator;
import java.util.List;

public final class Box {
    private final Matryoshka red = new Matryoshka("red");
    private final Matryoshka green = new Matryoshka("green");
    private final Matryoshka blue = new Matryoshka("blue");
    private final Matryoshka magenta = new Matryoshka("magenta");

    // Итератор для вывода сначала самых маленьких матрёшек каждого цвета
    public Iterator<String> getSmallFirstIterator() {
        return new Iterator<String>() {
            private int currentIndex = 0;
            private final int totalColors = 4; // количество цветов
            private int currentColorIndex = 0;
            private final List<Matryoshka> matryoshkas = List.of(red, green, blue, magenta);

            @Override
            public boolean hasNext() {
                return currentIndex <= 10;
            }

            @Override
            public String next() {
                String item = matryoshkas.get(currentColorIndex).getItems().get(currentIndex);
                currentColorIndex = (currentColorIndex + 1) % totalColors;
                if (currentColorIndex == 0) {
                    currentIndex++;
                }
                return item;
            }
        };
    }

    // Итератор для вывода сначала всех частей одной матрёшки от маленькой до большой, затем следующей
    public Iterator<String> getColorFirstIterator() {
        return new Iterator<String>() {
            private int currentColorIndex = 0;
            private int currentItemIndex = 0;
            private final List<Matryoshka> matryoshkas = List.of(red, green, blue, magenta);

            @Override
            public boolean hasNext() {
                return currentColorIndex < 4 && currentItemIndex <= 10;
            }

            @Override
            public String next() {
                String item = matryoshkas.get(currentColorIndex).getItems().get(currentItemIndex);

                currentItemIndex++;
                if (currentItemIndex == 11) { // если прошли все части матрёшки
                    currentItemIndex = 0;
                    currentColorIndex++;
                }
                return item;
            }
        };
    }
}
