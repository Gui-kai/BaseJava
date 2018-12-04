package com.example.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        Filter f1 = new Filter();
        f1.setId(10);   //查询为10的用户

        Filter f2 = new Filter();
        f2.setUserName("lucy"); //模糊查询用户名为lucy的用户

        Filter f3 = new Filter();
        f3.setEmail("guikai@qq.com,zh@163.com,77777@qq.com");//查询邮箱为其中任意一个的用户

        String sql1 = query(f1);
        String sql2 = query(f2);
        String sql3 = query(f3);

        System.out.println(sql1);
        System.out.println(sql2);
        System.out.println(sql3);

    }

    private static String query(Filter f) {
        StringBuffer sb = new StringBuffer();
        //1.获取到class
        Class c = f.getClass();
        //2.获取到table的名字
        boolean exists = c.isAnnotationPresent(Table.class);
        if (!exists) {
            return null;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value();
        sb.append("Select * from ").append(tableName).append(" where 1=1");
        //3.遍历所有的字段
        Field[] fArray = c.getDeclaredFields();
        for (Field field:fArray) {
            //4.处理每个字段对应的sql
            //4.1 拿到字段的名字
            boolean fExists = field.isAnnotationPresent(Column.class);
            if (!fExists) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            //4.2 拿到字段的值
            String filedName = field.getName();
            String getMethodName = "get" + filedName.substring(0, 1).toUpperCase()
                    + filedName.substring(1);
            Object fieldValue = null;
            try {
                Method getMethod = c.getMethod(getMethodName);
                fieldValue = (Object) getMethod.invoke(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //4.3 拼装sql
            if (fieldValue==null ||
                    (fieldValue instanceof  Integer && (Integer)fieldValue==0)) {
                continue;
            }
            sb.append(" and ").append(filedName);

            if (fieldValue instanceof String) {
                if (((String) fieldValue).contains(",")){
                    String[]  values = ((String) fieldValue).split(",");
                    sb.append(" in(");
                    for (String v:values) {
                        sb.append("'").append(v).append("'").append(",");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    sb.append(")");
                } else {
                    sb.append("=").append("'").append(fieldValue).append("'");
                }
            } else if (fieldValue instanceof Integer) {
                sb.append("=").append(fieldValue);
            }
        }
        return sb.toString();
    }
}
