package com.alliedtesting.etelenkov;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Iterators {
    public static void main(String[] args) {

        System.out.println(sum(take(getNonNegativeIntegersIterator(), 11)));

//        System.out.println(reduce2(take(getNonNegativeIntegersIterator(), 20),
//                (a, b) -> {
//                    a.add(b);
//                    return a;
//                }, new ArrayList<Integer>()));
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

    public static <T, E> Iterator<T> map(Iterator<E> it, Function<? super E, T> f) {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return f.apply(it.next());
            }
        };
    }

    public static Integer min(Iterator<Integer> it) {
        return reduce(it, (a, b) -> a < b ? a : b);
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

    public static <T, E> E reduce2(Iterator<T> it, BiFunction<E, T, E> bf, E initial) {
        E res = initial;
        while (it.hasNext()) {
            res = bf.apply(res, it.next());
        }
        return res;
    }

    public static <T> T reduce3(Iterator<T> it, BiFunction<T, T, T> bf) {
        // todo: ...
        return reduce2(it, (a, b) -> (a == null) ? b : bf.apply(a, b), null);
    }


//    public static String join(Iterator<?> it) {
//        String res = null;
//        while (it.hasNext()) {
//            String curr = it.next().toString();
//            if (res != null) res = res + curr;
//            else res = curr;
//        }
//        return res;
//    }

    /*
    join(Arrays.asList(4, 5, 6).iterator()) -> "456"
     */
}
