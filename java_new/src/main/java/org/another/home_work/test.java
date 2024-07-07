package org.another.home_work;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        char date = '3';
        List<String> list = new ArrayList<>() {
            {
                add("23.06.2020");
                add("23.06.2021");
                add("23.06.2022");
                add("23.06.2023");
            }
        };
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).charAt(9) < date) {
                System.out.println(list.get(i));
            }
        }
    }
}
