package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<T> {
    private T[] elements;
    private int size;

    private Comparator<T> comparator;

    public MyArrayList() {
        elements = getNewEmptyArray(10);
    }

    public MyArrayList(Comparator<T> comparator) {
        this();
        this.comparator = comparator;
    }


    /**
     * Adding element to last position in list
     *
     * @param element Element for adding
     */
    public void add(T element) {
        add(size, element);
    }

    /**
     * Adding element into list at the specified index
     *
     * @param index   Specified index
     * @param element Element for adding
     */
    public void add(int index, T element) {
        if (elements.length >= index)
            expand();

        System.arraycopy(elements, index,
                elements, index + 1,
                size - index);

        elements[index] = element;
        size++;
    }

    /**
     * Receiving element from specified index
     *
     * @param index Index of element
     * @return Requested element
     */
    public T get(int index) {
        if (elements.length >= index) {
            return elements[index];
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Receiving index of specified element
     *
     * @param element Specified element
     * @return Index of element. -1 if element not found
     */
    public int indexOf(T element) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] == null) continue;
            if (elements[i].equals(element))
                return i;
        }
        return -1;
    }

    /**
     * Remove element from list, if contains
     *
     * @param element Removable element
     */
    public void remove(T element) {
        var index = indexOf(element);
        if (index == -1) return;

        remove(index);
    }

    /**
     * Remove element from specified index
     *
     * @param index Index of removable element
     */
    public void remove(int index) {
        if (elements.length == 0) return;
        T[] updated = getNewEmptyArray(elements.length - 1);

        if (index >= 0)
            System.arraycopy(elements, 0, updated, 0, index);

        if (size - (index + 1) >= 0)
            System.arraycopy(elements, index + 1, updated, index, size - (index + 1));

        elements = updated;
        size = updated.length;
    }

    /**
     * Remove all elements from collection
     */
    public void clear() {
        elements = getNewEmptyArray(0);
        size = 0;
    }

    /**
     * Return amount of stored elements
     */
    public int size() {
        return size;
    }

    /**
     * Sort collection by merge-sort algorithm
     */
    public void sort() {
        sorting(elements, 0, elements.length - 1);
    }

    private void sorting(T[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            sorting(array, left, middle);
            sorting(array, middle + 1, right);

            merge(array, left, middle, right);
        }
    }

    private void merge(T[] array, int left, int middle, int right) {
        int sizeLeft = middle - left + 1;
        int sizeRight = right - middle;

        T[] leftArray = getNewEmptyArray(sizeLeft);
        T[] rightArray = getNewEmptyArray(sizeRight);

        System.arraycopy(array, left, leftArray, 0, sizeLeft);
        System.arraycopy(array, middle + 1, rightArray, 0, sizeRight);

        int i = 0;
        int j = 0;
        int k = left;

        while (i < sizeLeft && j < sizeRight) {
            if (compare(leftArray[i], rightArray[j]) <= 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < sizeLeft) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < sizeRight) {
            array[k] = rightArray[j];
            j++;
            k++;
        }


    }

    /**
     * Returns:
     * a negative integer when first less than second,
     * zero when first equal to second,
     * a positive integer when first greater than second
     */
    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (first instanceof Comparable) {
            Comparable<T> comparable = (Comparable<T>) first;
            return comparable.compareTo(second);
        }

        if (comparator != null) {
            return comparator.compare(first, second);
        }

        throw new RuntimeException("This collection cannot be sorted. \nYou must implement the Comparable interface for the stored elements or pass a Comparator via the constructor.");
    }


    @SuppressWarnings("unchecked")
    private T[] getNewEmptyArray(int capacity) {
        return (T[]) new Object[capacity];
    }

    /**
     * Represent storaging elements in text view
     *
     * @return Represent text
     */
    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

    private void expand() {
        var newSize = (int) (elements.length * 1.5f);
        elements = Arrays.copyOf(elements, newSize);
    }
}
