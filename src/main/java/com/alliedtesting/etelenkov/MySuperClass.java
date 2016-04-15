package com.alliedtesting.etelenkov;

/**
 * Created by etelenkov on 4/11/2016.
 */
public class MySuperClass {

    private int superClassPrivateField;
    int superClassDefaultField;

    public MySuperClass() {
        superClassPrivateField = 100;
        superClassDefaultField = 150;
    }

    public MySuperClass(int i) {
        superClassPrivateField = 10;
        superClassDefaultField = 15;
    }

    int getSuperClassPrivateField() {
        return superClassPrivateField;
    }

    void setSuperClassPrivateField(int i) {
        superClassPrivateField = i;
    }
}
