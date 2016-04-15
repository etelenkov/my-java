package com.alliedtesting.etelenkov;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class Main {
    public static void main(String... args) {
        ArrayList<Integer> arrListI = new ArrayList<>();
        arrListI.add(15);
        arrListI.add(25);
        arrListI.add(35);

        ArrayList<String> arrListS = new ArrayList<>();
        arrListS.add("45");
        arrListS.add("55");
        arrListS.add("65");

        List<? extends Object> list1 = arrListI;
        List<?> list2 = arrListS;

        printList(list1);
        printList(list2);

        List<?> list3 = Arrays.asList(15, 15.15, 20);
        printList(list3);

        Integer[] list4 = {1, 2, 3};
        swapInArray(list4, 0, 1);
        System.out.println(Arrays.asList(list4));

        String[] list5 = {"A", "B", "C"};
        swapInArray(list5, 2, 1);
        System.out.println(Arrays.asList(list5));

        List<String> ls = new ArrayList<>();
        ls.add("1");
        append(ls);
        //append(ls, String.class);
        System.out.println(Arrays.asList(ls) + "\n---");

        exceptionTest();
    }

    public static void exceptionTest() {

        System.out.println("exceptionTest: method started...");

        class AT implements AutoCloseable {

            private String status = "";

            @Override
            public void close() throws Exception {
                status = "Closed";
                //System.out.println("exceptionTest: AutoCloseable closed");
                throwErr();
                printStatus();
            }

            AT() throws Exception, Error {
                status = "Created";
                //System.out.println("exceptionTest: AutoCloseable created");
                printStatus();
                //throwEx();
                throwErr();
            }

            void throwEx() throws Exception {
                System.out.println("exceptionTest: AutoCloseable next step is to trow an Exception...");
                throw new Exception();
            }

            void throwErr() throws Error {
                System.out.println("exceptionTest: AutoCloseable next step is to trow an Error...");
                throw new Error();
            }

            void open() {
                status = "Opened";
                //System.out.println("exceptionTest: AutoCloseable opened");
                printStatus();
            }

            void printStatus() {
                System.out.println("exceptionTest: AutoCloseable: " + status);
            }
        }

        boolean finished = false;
        try (AT at = new AT()) {
            System.out.println("exceptionTest: entered try state...");
            at.open();
            //at.throwErr();
            //at.throwEx();
            finished = true;
            System.out.println("exceptionTest: finished try state.");
        } catch (Exception e) {
            System.out.println("exceptionTest: entered Exception catch state...");
            e.printStackTrace();
        } catch (Error e) {
            System.out.println("exceptionTest: entered Error catch state...");
            e.printStackTrace();
        } finally {
            if (finished)
                System.out.println("exceptionTest: entered finally state (try finished).");
            else
                System.out.println("exceptionTest: entered finally state (try FAILED).");
        }

        System.out.println("exceptionTest: method finished.");
    }

    public static <T> void swapInArray(T[] array, int i1, int i2) {
        if (i1 == i2) {
            System.out.println("swapInArray - indexes are equal - nothing to swap");
            return;
        }
        if (i1 >= array.length || i2 >= array.length) {
            System.out.println("swapInArray - error: indexes are out of bound");
            return;
        }

        // Swap elements
        T temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
        System.out.println("swapInArray - done");
    }

    // A -> C 1.2, B -> C 1.3
    public static <E> void append(List<E> list) {
        try {
            Class<E> classE = (Class<E>) list.get(0).getClass();
            list.add(classE.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static <E> void printList(List<E> list) {
        for (E i : list) System.out.print(i + " ");
        System.out.println();
    }

    @Test
    public void sortIntArrayTest() {
        ArrayList<Integer> arr = new ArrayList<>(asList(5, 4, 3, 2, 1));
        ArrayList<Integer> exp = new ArrayList<>(asList(1, 2, 3, 4, 5));
        Helper.sortStable(arr, (a, b) -> a > b);
        assertEquals(arr, exp, "Sort of int array is not OK!");
    }

    @Test
    public void sortStringArrayCaseSensitiveTest() {
        ArrayList<String> arr = new ArrayList<>(asList("C", "B", "A", "c", "b", "a"));
        ArrayList<String> exp = new ArrayList<>(asList("A", "B", "C", "a", "b", "c"));
        Helper.sortStable(arr, (a, b) -> a.compareTo(b) > 0);
        assertEquals(arr, exp,
                "Sort of String array (case sensitive) is not OK!");
    }

    @Test
    public void sortStringArrayCaseNonSensitiveTest() {
        ArrayList<String> arr = new ArrayList<>(asList("C", "B", "A", "c", "b", "a"));
        ArrayList<String> exp = new ArrayList<>(asList("A", "a", "B", "b", "C", "c"));
        Helper.sortStable(arr, (a, b) -> a.compareToIgnoreCase(b) > 0);
        assertEquals(arr, exp,
                "Sort (stable) of String array (ignore-case) is not OK!");
    }

    @Test
    public void sortUnstableStringArrayCaseNonSensitiveTest() {
        ArrayList<String> arr = new ArrayList<>(asList("C", "B", "A", "c", "b", "a"));
        ArrayList<String> arrInitial = new ArrayList<>(asList("C", "B", "A", "c", "b", "a"));
        Helper.Comparator<String> comparator = (a, b) -> a.compareToIgnoreCase(b) > 0;
        Helper.sortUnstable(arr, comparator);

        for (int i = 0; i < arr.size() - 1; i++) {
            if (comparator.compare(arr.get(i), arr.get(i + 1))) {
                fail("Array: { " + arr.toString() + " } ; Element with index " + i +
                        " & " + i + 1 + " are wrong sorted!");
            }
        }

        // todo: check that the result is permutation of the initial list. Use Maps.
    }

    @Test
    public void sumTest() {
        int a = 2, b = 3;
        int expectedSum = 5;
        int actualSum = Helper.sum(a, b);
        assertEquals(actualSum, expectedSum, "Sum is not OK");
    }

    @Test
    public void foo() {
        String[] strings = {"a"};
        Object[] objects = strings;

        String newS = "b";
        Object newO = newS;
        objects[0] = newO;

        //objects[0] = new Object();

        System.out.println(strings[0]);
    }

    @Test
    public void divTest() {
        Assert.fail("me failed");
        int a = 10 / 2;
        assertEquals(a, 5, "Div is not OK");
    }
}