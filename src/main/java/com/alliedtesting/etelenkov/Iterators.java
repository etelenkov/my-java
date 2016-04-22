package com.alliedtesting.etelenkov;

import java.util.*;
import java.util.function.*;

public class Iterators {
    public static void main(String[] args) {

        //System.out.println(sum(take(getNonNegativeIntegersIterator(), 11)));

//        System.out.println(toList(take(getNonNegativeIntegersIterator(), 11)));
//
//        LookAheadIterator it = new LookAheadIterator(take(getNonNegativeIntegersIterator(), 11));
//        while (it.hasNext()) {
//            if (it.hasNext()) System.out.print(" peek: " + it.peek());
//            System.out.print(" next: " + it.next());
//            if (it.hasNext()) System.out.print(" peek: " + it.peek());
//            System.out.println();
//        }

        //        System.out.println(reduce2(take(getNonNegativeIntegersIterator(), 20),
//                (a, b) -> {
//                    a.add(b);
//                    return a;
//                }, new ArrayList<Integer>()));

        ArrayList<Iterator<Integer>> arrOfIt = new ArrayList<>();
        arrOfIt.add(take(getNonNegativeIntegersIterator(), 5));
        arrOfIt.add(primeIntegers(take(getNonNegativeIntegersIterator(), 16)));
        arrOfIt.add(magicIntegers(take(getNonNegativeIntegersIterator(), 15)));
        // print initial iterators
        print(arrOfIt.get(0));
        print(arrOfIt.get(1));
        print(arrOfIt.get(2));
        // print created iterator
        Iterator<Integer> it = filter2(arrOfIt, (a, b) -> a <= b);
        while (it.hasNext()) System.out.print(it.next());

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
     * @param p        should return true if 1 and 2nd parameters in the right order, otherwise - false
     * @param <T>
     * @return
     */
    public static <T> Iterator<T> filter2(Collection<Iterator<T>> collOfIt, BiPredicate<? super T, ? super T> p) {
        return new Iterator<T>() {
            // array of LookAheadIterator's of initial iterators
            private ArrayList<LookAheadIterator<T>> iterators = new ArrayList<>();

            // initialization block
            {
                for (Iterator<T> it : collOfIt) iterators.add(new LookAheadIterator<T>(it));
            }

            @Override
            public boolean hasNext() {
                boolean hasNextFlag = false;

                for (int i = 0; i < iterators.size(); i++) {
                    if (iterators.get(i).hasNext()) hasNextFlag = true;
                    else {
                        iterators.remove(i); // removing empty iterator from the list
                        // decrease counter i because of removal current element
                        // (we need to check this index again, because now here the next element or nothing)
                        i--; // todo: may be added to previous command as iterators.remove(i--); ???
                    }
                }

                return hasNextFlag;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    // Find index of needed iterator with the next element
                    // with help of given BiPredicate testing (if returns false - then
                    int neededInd = 0; // element with index 0 is always here because hasNext() is true
                    for (int i = 1; i < iterators.size(); i++)
                        if (!p.test(iterators.get(neededInd).peek(), iterators.get(i).peek()))
                            neededInd = i;
                    // Return needed element of needed iterator
                    return iterators.get(neededInd).next();
                }

                // Throw NoSuchElementException if there is no more elements in the iterators
                throw new NoSuchElementException();
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

//    @Override
//    public T next() {
//        if (peeked) {
//            peeked = false; // after return of next, a peeked element will be the other one
//            return next;
//        }
//        return initial.next();
//    }

    @Override
    public T next() {
        T result = peek();
        peeked = false; // after return of the next, a peeked element will be the other one
        return result;
    }
}
