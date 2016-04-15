package com.alliedtesting.etelenkov;

public enum MyEnum { // class MyEnum extends Enum<MyEnum>
    ENUM_CONST_ONE (2,"Enum 1 name") {
        private int foo;

        @Override
        int getValue() {
            return 42;
        }

        public void setFoo(int foo) {
            this.foo = foo;
        }

        public void printFoo() {
            System.out.println(ENUM_CONST_ONE);
        }
    },
    ENUM_CONST_TWO (4,"Enum 2 name"),
    ENUM_CONST_THREE (6,"Enum 4 name");

    private int value;
    private String comment;

    MyEnum(int i, String s) {
        value = i;
        comment = s;
    }

    void printFoo() {
        //System.out.println(MyEnum.ENUM_CONST_ONE.);
    }

    int getValue() { return value; }
    String getComment() { return comment; }
}
