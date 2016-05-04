package com.alliedtesting.etelenkov;

import java.util.Arrays;
import java.util.Collection;

interface MySet {
    void add(Object o);
    void addAll(Collection c);
    default public void addIfAbsent(Object o) {

    }
}

class MySetImpl implements MySet {
    @Override
    public void add(Object o) {
        System.out.println("Calling MySetImpl.add");
    }

    @Override
    public void addAll(Collection c) {
        System.out.println("Calling MySetImpl.addAll");
        for (Object e : c) add(e);
    }

    public void addIfAbsent(Object o) {
        System.out.println("Calling MySetImpl.addIfAbsent");
    }
}

final class MyInstrumentedSetBad extends MySetImpl {
    private int count = 0;

    @Override
    public void add(Object o) {
        super.add(o);
        count++;
    }

    @Override
    public void addAll(Collection c) {
        super.addAll(c);
        count += c.size();
//        for (Object e : c) {
//            add(e);
//        }
    }

    public int getCount() {
        return count;
    }
}

class MySetWrapper implements MySet {
    private final MySet mySet;

    public MySetWrapper(MySet mySet) {
        this.mySet = mySet;
    }

    @Override
    public void add(Object o) {
        mySet.add(o);
    }

    @Override
    public void addAll(Collection c) {
        mySet.addAll(c);
    }
}

final class MyInstrumentedSetGood extends MySetWrapper {
    private int count = 0;

    public MyInstrumentedSetGood(MySet mySet) {
        super(mySet);
    }

    @Override
    public void add(Object o) {
        super.add(o);
        ++count;
    }

    @Override
    public void addAll(Collection c) {
        super.addAll(c);
        count += c.size();
    }

    public int getCount() {
        return count;
    }

    public static void main(String... args) {
        MyInstrumentedSetBad b = new MyInstrumentedSetBad();
        b.addAll(Arrays.asList("1", "2", "3"));
        System.out.println(b.getCount());

        MyInstrumentedSetGood g = new MyInstrumentedSetGood(new MySetImpl());
        g.addIfAbsent(null);
        g.addAll(Arrays.asList("1", "2", "3"));
        System.out.println(g.getCount());
    }
}
