package com.alliedtesting.etelenkov;

/**
 * Created by etelenkov on 4/11/2016.
 */
public class MySubClass extends MySuperClass {
    private int subClassPrivateField = 200;
    int subClassDefaultField = 250;
    int fieldFromSuperClass;

    public MySubClass() {
        fieldFromSuperClass = super.getSuperClassPrivateField();
    }

    public MySubClass(int i) {
        super(i);
        fieldFromSuperClass = super.getSuperClassPrivateField();
    }

    int getSubClassPrivateField() {
        return subClassPrivateField;
    }

    void setSubClassPrivateField(int i) {
        subClassPrivateField = i;
    }
}
