package ru.otus;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Box box = new Box();

        System.out.println("Small first iterator:");
        Iterator<String> smallFirstIterator = box.getSmallFirstIterator();
        while (smallFirstIterator.hasNext()) {
            System.out.println(smallFirstIterator.next());
        }

        System.out.println("\nColor first iterator:");
        Iterator<String> colorFirstIterator = box.getColorFirstIterator();
        while (colorFirstIterator.hasNext()) {
            System.out.println(colorFirstIterator.next());
        }
    }
}