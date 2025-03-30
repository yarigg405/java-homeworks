package org.example;

import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements List<T> {

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    @Override
    public boolean add(T t) {
        linkLast(t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkPositionIndex(index);

        if (index == size)
            linkLast(element);
        else
            linkBefore(element, getNode(index));
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(size, c);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addAll(int index, Collection<? extends T> c) {
        checkPositionIndex(index);
        Object[] temp = c.toArray();
        if (temp.length == 0) return false;

        Node<T> startInsertNode, finishInsertNode;
        if (index == size)   //add to last node
        {
            finishInsertNode = null;
            startInsertNode = last;
        } else {
            finishInsertNode = getNode(index);
            startInsertNode = finishInsertNode.prev;
        }

        for (var o : temp) {
            T t = (T) o;
            var newNode = new Node<T>();
            newNode.prev = startInsertNode;
            newNode.item = t;
            newNode.next = null;

            if (startInsertNode == null)
                first = newNode;
            else
                startInsertNode.next = newNode;
            startInsertNode = newNode;
            size++;
        }

        if (finishInsertNode == null)
            last = startInsertNode;
        else {
            startInsertNode.next = finishInsertNode;
            finishInsertNode.prev = startInsertNode;
        }

        return true;
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T set(int index, T element) {
        checkPositionIndex(index);
        getNode(index).item = element;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        int result = 0;
        if (o == null) {
            for (var node = first; node != null; node = node.next) {
                if (node.item == null)
                    return result;
                result++;
            }
        } else {
            for (var node = first; node != null; node = node.next) {
                if (o.equals(node.item))
                    return result;
                result++;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int result = size;
        if (o == null) {
            for (var node = last; node != null; node = node.prev) {
                result--;
                if (node.item == null)
                    return result;
            }
        } else {
            for (var node = last; node != null; node = node.prev) {
                result--;
                if (o.equals(node.item))
                    return result;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @Override
    public Object[] toArray() {
        var arr = new Object[size];
        int index = 0;
        for (var node = last; node != null; node = node.prev) {
            arr[index++] = node.item;
        }
        return arr;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = (T1[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }

        int i = 0;
        Object[] result = a;
        for (var x = first; x != null; x = x.next) {
            result[i++] = x.item;
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (var node = first; node != null; node = node.next) {
                if (node.item == null) {
                    removeNode(node);
                    return true;
                }
            }
        } else {
            for (var node = first; node != null; node = node.next) {
                if (node.item.equals(o)) {
                    removeNode(node);
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public T remove(int index) {
        T element = null;
        checkPositionIndex(index);

        int i = 0;
        for (var node = first; node != null; node = node.next) {
            i++;
            if (i == index) {
                element = node.item;
                removeNode(node);
            }
        }
        return element;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean isModified = false;
        for (var obj : c) {
            if (remove(obj)) {
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean isModified = false;
        for (var node = first; node != null; node = node.next) {
            if (!c.contains(node.item)) {
                removeNode(node);
                isModified = true;
            }
        }
        return isModified;
    }

    @Override
    public void clear() {
        for (var node = first; node != null; node = node.next) {
            var next = node.next;
            node.item = null;
            node.next = null;
            node.prev = null;
            node = next;
        }
        first = last = null;
        size = 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkPositionIndex(index);
        return new MyListIterator(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkPositionIndex(fromIndex);
        checkPositionIndex(toIndex);

        try {
            var result = (MyLinkedList<T>) this.clone();
            int index = 0;
            for (var node = result.first; node != null; node = node.next) {
                if (index < fromIndex || index > toIndex) {
                    result.removeNode(node);
                }
            }

            return result;
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
            return new MyLinkedList<>();
        }
    }

    public T removeFirst() {
        var element = first.item;
        removeNode(first);
        return element;
    }

    public T removeLast() {
        var element = last.item;
        removeNode(last);
        return element;
    }

    @Override
    public String toString() {
        Iterator<T> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }


    @Override
    public void sort(Comparator<? super T> c) {
        quickSort(first, last, c);
    }

    private void quickSort(Node<T> start, Node<T> finish, Comparator<? super T> c) {
        if (start == null || start == finish) return;

        var middlePrev = middleOfQuickSort(start, finish, c);
        quickSort(start, middlePrev, c);

        if (middlePrev != null && middlePrev.next != null)
            quickSort(middlePrev.next, finish, c);
    }

    private Node<T> middleOfQuickSort(Node<T> start, Node<T> finish, Comparator<? super T> c) {
        if (start == finish || start == null || finish == null)
            return start;

        var prev = start;
        var curr = start;
        T item = finish.item;

        while (start != finish) {
            if (c.compare(start.item, item) <= 0) {
                prev = curr;
                var temp = curr.item;
                curr.item = start.item;
                start.item = temp;
                curr = curr.next;
            }
            start = start.next;
        }

        T temp = curr.item;
        curr.item = item;
        finish.item = temp;

        return prev;
    }


    /// //////////////////////////
    private Node<T> getNode(int index) {
        Node<T> result;
        if (index < (size >> 1)) {
            result = first;
            for (int i = 0; i < index; i++)
                result = result.next;
            return result;
        } else {
            result = last;
            for (int i = size - 1; i > index; i--)
                result = result.prev;
            return result;
        }
    }

    private void linkLast(T t) {
        final var l = last;
        final var newNode = new Node<T>();
        newNode.item = t;
        newNode.prev = last;
        newNode.next = null;
        last = newNode;

        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    private void linkBefore(T t, Node<T> point) {
        final var prev = point.prev;
        final var newNode = new Node<T>();
        newNode.prev = prev;
        newNode.item = t;
        newNode.next = point;
        point.prev = newNode;
        if (prev == null)
            first = newNode;
        else
            prev.next = newNode;
        size++;
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void removeNode(Node<T> node) {
        var prev = node.prev;
        var next = node.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            node.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }

        node.item = null;
        size--;
    }


    private static class Node<T> {
        private T item;
        private Node<T> next;
        private Node<T> prev;
    }


    private class MyListIterator implements ListIterator<T> {
        private Node<T> lastReturned;
        private Node<T> next;
        private int nextIndex;

        private MyListIterator(int index) {
            next = (index == size) ? null : getNode(index);
            nextIndex = index;
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();

            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.item;
        }

        public boolean hasPrevious() {

            return nextIndex > 0;
        }

        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }

            lastReturned = next = (next == null) ? last : next.prev;
            nextIndex--;
            return lastReturned.item;
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();

            var lastNext = lastReturned.next;
            removeNode(lastReturned);
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = null;
        }

        @Override
        public void set(T t) {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            lastReturned.item = t;
        }

        @Override
        public void add(T t) {
            lastReturned = null;
            if (next == null) {
                linkLast(t);
            } else {
                linkBefore(t, next);
            }
            nextIndex++;
        }
    }
}
