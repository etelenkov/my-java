package com.alliedtesting.etelenkov;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import static com.alliedtesting.etelenkov.Iterators.*;
import static java.util.Arrays.asList;
import static org.testng.Assert.*;

public class MainTest {
    public static void main(String... args) {
//        ArrayList<Integer> arrListI = new ArrayList<>();
//        arrListI.add(15);
//        arrListI.add(25);
//        arrListI.add(35);
//
//        ArrayList<String> arrListS = new ArrayList<>();
//        arrListS.add("45");
//        arrListS.add("55");
//        arrListS.add("65");
//
//        List<? extends Object> list1 = arrListI;
//        List<?> list2 = arrListS;
//
//        printList(list1);
//        printList(list2);
//
//        List<?> list3 = Arrays.asList(15, 15.15, 20);
//        printList(list3);
//
//        Integer[] list4 = {1, 2, 3};
//        swapInArray(list4, 0, 1);
//        System.out.println(Arrays.asList(list4));
//
//        String[] list5 = {"A", "B", "C"};
//        swapInArray(list5, 2, 1);
//        System.out.println(Arrays.asList(list5));
//
//        List<String> ls = new ArrayList<>();
//        ls.add("1");
//        append(ls);
//        //append(ls, String.class);
//        System.out.println(Arrays.asList(ls) + "\n---");
//
//        exceptionTest();

//        Set<String> setS1 = new LinkedHashSet<>();
//        setS1.add("E");
//        setS1.add("D");
//        setS1.add("A");
//        setS1.add("B");
//        setS1.add("C");
//        setS1.add("C");
//        System.out.println("setS1: " + setS1);
//        Iterator<String> it = setS1.iterator();
//        while (it.hasNext()) System.out.print(" " + it.next());
//
//
//
//        List<String> listS1 = new ArrayList<>();
//        listS1.add("E");
//        listS1.add("D");
//        listS1.add("A");
//        listS1.add("B");
//        listS1.add("C");
//        System.out.println("listS1: " + listS1);
//
//
//        System.out.println("Равны? : " + setS1.equals(listS1));
//
//        Collections.sort(listS1);
//        ListIterator<String> iter = listS1.listIterator();
//
////        System.out.println("--- 0 ---");
////        System.out.println("hasNext: " + iter.hasNext() + " nextIndex: " + iter.nextIndex());
////        System.out.println("hasPrevious: " + iter.hasPrevious() + " previousIndex: " + iter.previousIndex());
////
////        iter.next();
////        iter.next();
////        iter.next();
////        System.out.println("--- 3 ---");
////        System.out.println("hasNext: " + iter.hasNext() + " nextIndex: " + iter.nextIndex());
////        System.out.println("hasPrevious: " + iter.hasPrevious() + " previousIndex: " + iter.previousIndex());
////
////        iter.previous();
////        iter.previous();
////        System.out.println("--- 1 ---");
////        System.out.println("hasNext: " + iter.hasNext() + " nextIndex: " + iter.nextIndex());
////        System.out.println("hasPrevious: " + iter.hasPrevious() + " previousIndex: " + iter.previousIndex());
//
//        boolean finished = false;
//        boolean forward = true;
//        while (!finished) {
//            if (forward) {
//                if (iter.hasNext()) System.out.println(iter.next());
//                else forward = false;
//            } else {
//                if (iter.hasPrevious()) System.out.println(iter.previous());
//                else finished = true;
//            }
//        }

        int total = 100;
        String[] s = new String[total];
        for (int i = 0; i < total; i++) {
            s[i] = "String_" + (int) (Math.random() * 10);
        }
//        s[0] = "String32";
//        s[1] = "String14";
//        s[2] = "String23";
//        s[3] = "String14";
//        s[4] = "String41";
//        s[5] = "String23";
//        s[6] = "String14";
//        s[7] = "String14";
//        s[8] = "String32";
//        s[9] = "String23";
        MapExample(s);

        //Collections
//        Name nameArray[] = {
//                new Name("John", "Smith"),
//                new Name("Karl", "Ng"),
//                new Name("Jeff", "Smith"),
//                new Name("Tom", "Rich")
//        };
//        Set<Name> ts = new TreeSet<>();
//        Collections.sort(ts);

    }

//    class Employee implements Comparable<Employee> {
//        public Name name()     { ... }
//        public int number()    { ... }
//        public Date hireDate() { ... }
//        ...
//    }

    class Name implements Comparable<Name> {
        private final String firstName, lastName;

        public Name(String firstName, String lastName) {
            if (firstName == null || lastName == null)
                throw new NullPointerException();
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String firstName() {
            return firstName;
        }

        public String lastName() {
            return lastName;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Name))
                return false;
            Name n = (Name) o;
            return n.firstName.equals(firstName) && n.lastName.equals(lastName);
        }

        public int hashCode() {
            return 31 * firstName.hashCode() + lastName.hashCode();
        }

        public String toString() {
            return firstName + " " + lastName;
        }

        public int compareTo(Name n) {
            int lastCmp = lastName.compareTo(n.lastName);
            return (lastCmp != 0 ? lastCmp : firstName.compareTo(n.firstName));
        }
    }

    public static void MapExample(String... strings) {

        Map<String, Integer> map = new HashMap<>();

        for (String s : strings) {
            // Get repeats of the string
            Integer repeats = map.get(s);
            if (repeats == null) repeats = 1;
            else repeats++;
            // add new repeat value for the string key
            map.put(s, repeats);
        }

        // Print map
        for (Map.Entry<String, Integer> kv : map.entrySet()) {
            System.out.println(kv.getKey() + " repeats: " + kv.getValue());
        }

    }

    public static void exceptionTest() {

        System.out.println("exceptionTest: method started...");

        class AT implements AutoCloseable {

            private String status = "";

            AT() throws Exception, Error {
                status = "Created";
                //System.out.println("exceptionTest: AutoCloseable created");
                printStatus();
                //throwEx();
                throwErr();
            }

            @Override
            public void close() throws Exception {
                status = "Closed!";
                //System.out.println("exceptionTest: AutoCloseable closed");
                throwErr();
                printStatus();
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

    public static <T extends Throwable> void unchecked(Throwable t) throws T {
//        IllegalArgumentException i;// = new IllegalArgumentException();
//        RuntimeException r = new RuntimeException();
//        Exception e = new Exception();
//
//        i = e;
//        e = r;

        //IllegalArgumentException e = (IllegalArgumentException) new Exception();

        throw (T) t;
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
        ArrayList<String> exp = new ArrayList<>(asList("A", "a", "B", "b", "C", "c"));
        Helper.Comparator<String> comparator = (a, b) -> a.compareToIgnoreCase(b) > 0;
        Helper.sortUnstable(arr, comparator);

        for (int i = 0; i < arr.size() - 1; i++) {
            if (comparator.compare(arr.get(i), arr.get(i + 1))) {
                fail("Array: { " + arr.toString() + " } ; Element with index " + i +
                        " & " + i + 1 + " are wrong sorted!");
            }
        }

        if (!Helper.PermutedCollections(arr, exp)) {
            fail("Array: { " + arr.toString() + " } is not permutation of expected array: { " +
                    exp.toString() + " }. So, the sort operation FAILED!");
        }

    }

    // Iterable it1, Iterable it2, boolean result

    @Test(dataProvider = "PermutedCollectionsTest")
    public <T> void PermutedCollectionsTest(Iterable<T> it1, Iterable<T> it2, boolean exp) {
        assertEquals(Helper.PermutedCollections(it1, it2), exp,
                "PermutedCollections(...) FAILED for: \n" +
                        "\tIterable it1: { " + it1.toString() + " }\n" +
                        "\tIterable it2: { " + it2.toString() + " }\n" +
                        "The result should be: " + exp +
                        " (true - permuted; false - not permuted collections");
    }

    /**
     * DataProvider "PermutedCollectionsTest":
     * 1 - Iterable 1
     * 2 - Iterable 2
     * 3 - boolean result
     *
     * @return Object[][] DataProvider
     */
    @DataProvider(name = "PermutedCollectionsTest")
    public Object[][] createData1() {
        return new Object[][]{
                // --- 1 ---
                {asList("C", "B", "A", "c", "b", "a"),
                        asList("A", "a", "B", "b", "C", "c"),
                        true},
                // --- 2 ---
                {asList("C", "B", "A", "c", "b", "a"),
                        asList("A", "A", "B", "b", "C", "c"),
                        false},
                // --- 3 ---
                {asList(),
                        asList(),
                        true},
        };
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
        int a = 10 / 2;
        assertEquals(a, 5, "Div is not OK");
    }

    @Test
    public void fooBar() {
        this.<IllegalArgumentException>unchecked(new Exception("hello!"));
    }

    @Test
    public void runnable1Test() {
        Runnable o1 = getRunnable1();
        Runnable o2 = getRunnable1();
        assertNotSame(o1, o2, "getRunnable1() returned two objects of the same instance. Test Failed!");
    }

    @Test
    public void runnable2Test() {
        Runnable o1 = getRunnable2();
        Runnable o2 = getRunnable2();
        // two lambdas with enclosure should be different objects
        assertSame(o1, o2, "getRunnable2() returned two objects of different instances. Test Failed!");
    }

    @Test
    public void runnable3Test() {
        Runnable o1 = getRunnable3();
        Runnable o2 = getRunnable3();
        // two lambdas with enclosure should be different objects
        assertNotSame(o1, o2, "getRunnable3() returned two objects of the same instance. Test Failed!");
    }

    @Test
    public void runnable4Test() {
        Supplier<Runnable> asdf = getRunnable4("asdf");
        Runnable o1 = asdf.get();
        Runnable o2 = asdf.get();
        // two lambdas with enclosure should be different objects
        assertNotSame(o1, o2);
    }

    @Test
    public void iteratorsFilter2_1Test() {
        // Create collection of iterators
        ArrayList<Iterator<Integer>> arrOfIt = new ArrayList<>();
        arrOfIt.add(Arrays.asList(0, 1, 2, 3, 4).iterator());
        arrOfIt.add(Arrays.asList(0, 3, 6, 9, 12, 15).iterator());
        arrOfIt.add(Arrays.asList(0, 5, 10).iterator());

        // Create comparator
        BiPredicate<Integer, Integer> comparator = (a, b) -> a <= b;

        // Create expecting iterator
        List<Integer> exp = Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 155);

        // Get result iterator
        List<Integer> res = toList(filter2(arrOfIt, comparator));

        // Check the results
        assertEquals((Object)res, (Object)exp, "Iterators.filter2(...) FAILED!");
    }

    @Test
    public void iteratorsFilter2_2Test() {
        // Create collection of iterators
        ArrayList<Iterator<String>> arrOfIt = new ArrayList<>();
        arrOfIt.add(Arrays.asList("B", "B", "C", "Y").iterator());
        arrOfIt.add(Arrays.asList("F", "X", "Z").iterator());
        arrOfIt.add(Arrays.asList("A", "Q", "S", "V", "W").iterator());

        // Create comparator
        BiPredicate<String, String> comparator = (a, b) -> a.compareTo(b) <= 0;

        // Create expecting iterator
        List<String> exp = Arrays.asList("A", "B", "B", "C", "F", "Q", "S", "V", "W", "X", "Y", "Z");

        // Get result iterator
        List<String> res = toList(filter2(arrOfIt, comparator));

        // Check the results
        assertEquals((Object)res, (Object)exp, "Iterators.filter2(...) FAILED!");
    }
}