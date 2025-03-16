package org.example;


public class Main {
    public static void main(String[] args) {
        var myList = new MyArrayList<Integer>();

        myList.add(1);
        myList.add(6);
        myList.add(8);
        myList.add(2);
        myList.add(14);
        myList.add(10);
        myList.add(1);
        myList.add(11);
        myList.add(12);
        myList.add(9);
        myList.add(4);
        myList.add(15);
        myList.add(7);
        myList.add(7);

        System.out.println(myList);
        System.out.println("\n\n\n");

        myList.sort();
        System.out.println(myList);
    }
}