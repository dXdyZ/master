package org.another;

import java.util.Arrays;
import java.util.List;

public class MyCollection<T> {
    static int DEFAULT_CAPACITY = 5;
    static int size = 2;
    static Object[] array;

    public MyCollection() {
        array = new Object[DEFAULT_CAPACITY];
    }

    public MyCollection(int capacity) {
        DEFAULT_CAPACITY = capacity;
        array = new Object[DEFAULT_CAPACITY];
    }

    public Object get(int index) {
        if (index <= array.length) {
            return array[index];
        } else {
            throw new NullPointerException("Такого элемента нет");
        }
    }

    public int size() {
        return array.length;
    }

    public void printAll() {
        for (Object o : array) {
            System.out.println(o);
        }
    }

    public void add(T o) {
        int count = 0;
        if (array[array.length - 1] != null) {
            array = Arrays.copyOf(array, DEFAULT_CAPACITY * size);
            size++;
        }
        if (array[0] != null) {
            while (array[count] != null) {
                count++;
            }
            array[count] = o;
        } else {
            array[0] = o;
        }
    }

    public Object[] arraySorted() {
        Object[] newArray =  Arrays.stream(array).sorted().toArray();
        return newArray;
    }

    public Integer[] arraySortedNumber() {
        Integer[] newArrayInt = castNewIntegerArray();
        for (int i = 0; i < newArrayInt.length - 1; i++) {
            for (int j = 0; j < newArrayInt.length - 1 - i; j++) {
                if (newArrayInt[j] > newArrayInt[j + 1]) {
                    // Обмен элементов array[j] и array[j + 1]
                    int temp = newArrayInt[j];
                    newArrayInt[j] = newArrayInt[j + 1];
                    newArrayInt[j + 1] = temp;
                }
            }
        }
        return newArrayInt;
    }

    public Object remove(Object o) {
        Object newOb = null;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(o)) {
                newOb = array[i];
                array[i] = null;
                break;
            } else {
                throw new NullPointerException("Такого элемета в коллекции нету");
            }
        }
        return newOb;
    }

    public void clear() {
        array = Arrays.copyOf(array, DEFAULT_CAPACITY);
        size = 2;
        Arrays.fill(array, null);
    }

    public void postponeList(List<T> lo) {
        for (T o : lo) {
            add(o);
        }
    }

    public int min() {
        Integer[] newArrayInt = castNewIntegerArray();
        int min = newArrayInt[0];
        for (int i = 1; i < newArrayInt.length; i++) {
            if (newArrayInt[i] < min) {
                min = newArrayInt[i];
            }
        }
        return min;
    }


    public int max() {
        Integer[] newArrayInt = castNewIntegerArray();
        int max = newArrayInt[0];
        for (int i = 1; i < newArrayInt.length; i++) {
            if (newArrayInt[i] > max) {
                max = newArrayInt[i];
            }
        }
        return max;
    }

    public Object last() {
        return array[array.length - 1];
    }
    public Object first() {
        return array[0];
    }

    private Integer[] castNewIntegerArray() {
        int count = 0;
        Integer[] newArrayInt = new Integer[array.length];
        for (Object obj : array) {
            if (obj instanceof Integer) {
                newArrayInt[count++] = (Integer) obj;
            }
        }
        return newArrayInt;
    }
}

