package org.another;


import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        Item<String, ? extends List> item = new Item<>("45", list);
        System.out.println(item.getId().getClass() + " " + item.getSumma());
    }

    public static <T> T  test (T e) {
        return e;
    }
}

class Item<T, E> {
    private T id;
    private E summa;
    public Item(T id, E summa) {
        this.id = id;
        this.summa = summa;
    }
    public T getId() {
        return id;
    }
    public E getSumma() {
        return summa;
    }
}