package com.ann.test;

import java.lang.annotation.*;

/**
 * 我们自定义一个注解的基本格式
 */

@Target({ElementType.METHOD,ElementType.TYPE})  //可以指定被注解的类型
@Retention(RetentionPolicy.RUNTIME)             //注解类型：运行时、编译时
@Inherited   //是否允许子类继承注解 接口不起作用 需要是类继承
@Documented

//如果注解只有一个成员，则这个成员名字必须取名为value()，方便在使用时的默认参数
public @interface Description {

    //成员必须无参 无异常方式声明
    String desc();

    //成员类型是受限的，合法类型:String Class Annotation Enumeration
    String author();

    int age() default 18;
}
