package com.alliedtesting.etelenkov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {
    public static int sum(int a, int b) {
        return a + b;
    }

    public static <T> void sortUnstable(List<T> objects, Comparator<? super T> c) {
        for (int i = 0; i < objects.size(); ++i) {
            for (int j = i + 1; j < objects.size(); ++j) {
                if (c.compare(objects.get(i), objects.get(j))) {
                    T temp = objects.get(i);
                    objects.set(i, objects.get(j));
                    objects.set(j, temp);
                }
            }
        }
    }

    public static <T> void sortStable(ArrayList<T> objects, Comparator<? super T> c) {
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < objects.size(); i++) {
                if (c.compare(objects.get(i - 1), objects.get(i))) {
                    T temp = objects.get(i);
                    objects.set(i, objects.get(i - 1));
                    objects.set(i - 1, temp);
                    swapped = true;
                }
            }
        }
    }

    public interface Comparator<T> {
        // compares two objects and returns:
        // true - if o1 > o2
        // false - if o1 <= o2
        boolean compare(T o1, T o2);
    }

    public static <T> boolean PermutedCollections(Iterable<T> it1, Iterable<T> arr2) {

        Map<T, Integer> map1 = new HashMap<>();
        Map<T, Integer> map2 = new HashMap<>();

        for (T t : it1) {
            Integer rep = map1.get(t);
            map1.put(t, (rep == null ? 1 : ++rep));
        }

        for (T t : arr2) {
            Integer rep = map2.get(t);
            map2.put(t, (rep == null ? 1 : ++rep));
        }

//        System.out.println(map1.entrySet());
//        System.out.println(map2.entrySet());

        return map1.equals(map2);
    }
}
