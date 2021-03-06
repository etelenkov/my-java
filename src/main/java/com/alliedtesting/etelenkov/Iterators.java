package com.alliedtesting.etelenkov;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;


class Foo extends Person {
    private final static Foo INSTANCE = new Foo();

    private Foo() {
        System.out.println();
    }

    public static Foo getInstance() {
        return INSTANCE;
    }

    private final Object finalizeGuardian = new Object() {
        protected void finalize() throws Throwable {
            System.out.println("Finilize Guardian: making internal finalizations...");

            System.out.println("Finilize Guardian: colling super.finilize() ...");
            super.finalize();

            System.out.println("Finilize Guardian: done all the job!");
        }
    };


}

class Person {
    String firstName;
    String lastName;
    int yearOfBirth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (yearOfBirth != person.yearOfBirth) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        return lastName != null ? lastName.equals(person.lastName) : person.lastName == null;

    }

    public Person() {
        int i = 123;
        Integer a = 42;
        i++;
        System.out.println("Person");
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + yearOfBirth;
        return result;
    }
}

public class Iterators {
    public static void main(String[] args) {

//        ArrayList<Iterator<Integer>> arrOfIt = new ArrayList<>();
//        arrOfIt.add(take(getNonNegativeIntegersIterator(), 5));
//        arrOfIt.add(primeIntegers(take(getNonNegativeIntegersIterator(), 16)));
//        arrOfIt.add(magicIntegers(take(getNonNegativeIntegersIterator(), 15)));

        Person o1 = Foo.getInstance();
        Person o2 = new Person();

        Object o = o1;

        System.out.println(o.getClass());
        System.out.println(o instanceof Collections);

        System.out.println(o1 == o2);
        System.out.println("getClass(): " + (o1.getClass() == o2.getClass()));
        System.out.println("instanceof: " + (o1 instanceof Foo));

        List<Integer> list =
                Arrays.asList(0, 0, 0, 1, 2, 3, 3, 4, 5, 6, 9, 10, 12, 15);
        Integer res = list.stream()
                .filter(e -> e % 3 == 0)
                .map(e -> e == 3 ? 100 : e)
                .reduce(0, (e, r) -> r + e);
        System.out.println(res); //242

        List<Integer> res1 = list.stream()
                .filter(e -> e % 3 == 0)
                .map(e -> e == 3 ? 100 : e)
                .collect(Collectors.toList());
        System.out.println(res1);

    }


    public static Runnable getRunnable1() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable1 (use anonymous)");
            }
        };
    }

    public static Runnable getRunnable2() {
        return () -> System.out.println("Runnable2 (lambda)");
    }

    public static Runnable getRunnable3() {
        String s = "closure";
        return () -> System.out.println("Runnable3 (lambda with closure): " + s);
    }

    public static <T> Supplier<Runnable> getRunnable4(String message) {
        return new Supplier<Runnable>() {
            @Override
            public Runnable get() {
                return () -> System.out.println(message);
            }
        };
    }

    public static Iterator<Integer> getNonNegativeIntegersIterator() {
        return new Iterator<Integer>() {
            private Integer previous = -1; // start value to return next as 0 (ZERO)

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return ++previous;
            }
        };
    }

    public static <T> void forEach(Iterator<T> it, Consumer<? super T> cons) {
        while (it.hasNext()) cons.accept(it.next());
    }

    public static void print(Iterator<?> it) {
        forEach(it, System.out::println);
    }

    public static <T> Iterator<T> take(Iterator<T> it, int bound) {
        return new Iterator<T>() {
            private int count = 0;

            @Override
            public boolean hasNext() {
                return (count < bound) && it.hasNext();
            }

            @Override
            public T next() {
                if (hasNext()) {
                    count++;
                    return it.next();
                }
                throw new NoSuchElementException();
            }
        };
    }

    // region Provided by 3rd party libs...
    public static boolean isPrime(Integer i) {
        return i % 3 == 0;
        //throw new UnsupportedOperationException();
    }

    public static boolean isPalindrome(Integer i) {
        return i % 4 == 0;
        //throw new UnsupportedOperationException();
    }

    public static boolean isMagicNumber(Integer i) {
        return i % 5 == 0;
        //throw new UnsupportedOperationException();
    }
    // endregion

    public static Iterator<Integer> primeIntegers(Iterator<Integer> it) {
        return filter(it, Iterators::isPrime);
    }

    public static Iterator<Integer> palindromeIntegers(Iterator<Integer> it) {
        return filter(it, Iterators::isPalindrome);
    }

    public static Iterator<Integer> magicIntegers(Iterator<Integer> it) {
        return filter(it, Iterators::isMagicNumber);
    }

    public static <T> Iterator<T> filter(Iterator<T> it, Predicate<? super T> p) {
        return new Iterator<T>() {
            private T next;
            private boolean hasNextFlag = false;

            @Override
            public boolean hasNext() {
                if (!hasNextFlag)
                    while (it.hasNext()) {
                        next = it.next();
                        if (p.test(next)) {
                            hasNextFlag = true;
                            break;
                        }
                    }
                return hasNextFlag;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    hasNextFlag = false;
                    return next;
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static Iterator<String> integerStrings(Iterator<Integer> it) {
        return map(it, a -> a.toString());
    }

    public static Iterator<Integer> numsOfDigits(Iterator<Integer> it) {
        return map(it, a -> a.toString().length());
    }

    public static Iterator<Integer> squares(Iterator<Integer> it) {
        return map(it, a -> a * a);
    }

    public static <E, R> Iterator<R> map(Iterator<E> it, Function<? super E, R> f) {
        return new Iterator<R>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public R next() {
                return f.apply(it.next());
            }
        };
    }

    public static Integer min(Iterator<Integer> it) {
        return min(it, (a, b) -> a.compareTo(b) <= 0);
    }

    /**
     * @param it         Iterator of elements of type <T> to compare
     * @param comparator Comparator function with two elements of Iterator's
     *                   sequence (e1, e2) as parameters: returns boolean:
     *                   true - if a pare of elements (e1, e1) are in the right order
     *                   false - if a pare of elements (e1, e2) are in the wrong order
     * @param <T>        Type of elements of iterator's sequence
     * @return Minimal element of type <T> of iterator's sequence after comparison
     * (after this function return Iterator is empty)
     * @throws NoSuchElementException if the iteration has no elements
     */
    public static <T> T min(Iterator<T> it, BiPredicate<? super T, ? super T> comparator) {
        return reduce2(it, (a, b) -> comparator.test(a, b) ? a : b, it.next());
    }

    public static Integer max(Iterator<Integer> it) {
        return reduce(it, (a, b) -> a > b ? a : b);
    }

    public static Integer sum(Iterator<Integer> it) {
        return reduce(it, (a, b) -> a + b);
    }

    public static Integer product(Iterator<Integer> it) {
        return reduce(it, (a, b) -> a * b);
    }

    public static <T> T reduce(Iterator<T> it, BiFunction<T, T, T> bf) {
        T res = null;
        while (it.hasNext()) {
            T curr = it.next();
            if (res != null) res = bf.apply(res, curr);
            else res = curr;
        }
        return res;
    }

    public static <T, R> R reduce2(Iterator<T> it, BiFunction<R, T, R> bf, R initial) {
        R res = initial;
        while (it.hasNext()) {
            res = bf.apply(res, it.next());
        }
        return res;
    }

    public static <T> T reduce3(Iterator<T> it, BiFunction<T, T, T> bf) {
        return reduce2(it, (a, b) -> (a == null) ? b : bf.apply(a, b), null);
    }


    public static <T> List<T> toList(Iterator<T> it) {
        return reduce2(it, (a, b) -> {
            a.add(b);
            return a;
        }, new ArrayList<T>());
    }

    /**
     * @param collOfIt
     * @param comparator should return true if 1 and 2nd parameters in the right order, otherwise - false
     * @param <T>
     * @return
     */
    public static <T> Iterator<T> filter2(Collection<Iterator<T>> collOfIt, BiPredicate<? super T, ? super T> comparator) {
        return new Iterator<T>() {
            // array of LookAheadIterator's of initial iterators
            private List<LookAheadIterator<T>> lookAheadIt =
                    toList(map(collOfIt.iterator(), LookAheadIterator::new));

            @Override
            public boolean hasNext() {
                return filter(lookAheadIt.iterator(), LookAheadIterator::hasNext).hasNext();
            }

            @Override
            public T next() {
                return min(filter(lookAheadIt.iterator(), LookAheadIterator::hasNext),
                        (a, b) -> comparator.test(a.peek(), b.peek())).next();
            }
        };
    }
}

class LookAheadIterator<T> implements Iterator<T> {
    private final Iterator<T> initial;
    private T next = null; // peeked element
    private boolean peeked = false; // peeked flag

    public LookAheadIterator(Iterator<T> initial) {
        this.initial = initial;
    }

    /**
     * Before every call of peek() method you should check for hasNext() true value
     * to avoid NoSuchElementException to be thrown if there is no more next
     * elements available (the same as with a call of next() method of
     * Iterator<T> implementation)
     */
    public T peek() {
        if (!peeked) {
            next = initial.next(); // throws NoSuchElementException if the iteration has no more elements
            peeked = true;
        }
        return next;
    }

    @Override
    public boolean hasNext() {
        return peeked || initial.hasNext();
    }

    @Override
    public T next() {
        T result = peek();
        peeked = false; // after return of the next, a peeked element will be the other one
        return result;
    }
}
