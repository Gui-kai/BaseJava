package com.ann.test;

@Description(desc = "I am is interface",author = "mooc boy")
public class Person {

    @Description(desc = "I am is interface method",author = "mooc boy")
    public String name(){
        return null;
    }

    public int age(){
        return 0;
    }

    @Deprecated
    public void sing(){

    }
}
