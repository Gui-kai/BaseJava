package com.ann.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

//解析注解:通过反射获取类、函数或成员上的运行时注解信息，从而实现动态控制程序运行时的逻辑
public class ParseAnn {

    public static void main(String[] args) {
        //一、使用类解析器加载类
        try {
            Class c = Class.forName("com.ann.test.Child");
            //找到类上面的注解
            //1.isAnnotationPresent是判断这个类上面是否存在Description这个注解
            boolean isExist = c.isAnnotationPresent(Description.class);
            if (isExist) {
                //拿到注解实例
                Description d = (Description) c.getAnnotation(Description.class);
                System.out.println(d.desc());
            }

            //从反射出来的类中获取方法，然后获取该方法上的注解
            Method[] ms = c.getMethods();
            for (Method m:ms) {
                boolean isMExist = m.isAnnotationPresent(Description.class);
                if (isMExist) {
                    Description d = m.getAnnotation(Description.class);
                    System.out.println(d.desc());
                }
            }

            //另外一种解析方法
            for (Method m:ms) {
                Annotation[] as = m.getAnnotations();
                for (Annotation a:as) {
                    if (a instanceof Description) {
                        Description d = (Description)a;
                        System.out.println(d.desc());
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
