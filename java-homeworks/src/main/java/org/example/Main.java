package org.example;


import java.util.Comparator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        var list = new MyLinkedList<String>();

        list.add("Thomas");
        list.add("Gerbert");
        list.add("Fiffy");
        list.add("Veronique");
        list.add("Gabby");
        list.add("Henderson");
        list.add("Brian");
        list.add("Wans");
        list.add(1, "Valentine");

        list.remove(4);
        list.set(3, "Michael");

        for (var name : list) {
            System.out.println(name);
        }

        list.sort(Comparator.naturalOrder());
        System.out.println(list);
    }
}