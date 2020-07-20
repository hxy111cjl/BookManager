package com.oracle.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class GetMybatis {
    private static SqlSessionFactory factory;//创建SqlSessionFactory对象
    private static ThreadLocal<SqlSession> threadLocal;//声明ThreadLocal对象
    //使用static代码域来为静态属性赋值
    static{
        try {
            InputStream is = Resources.getResourceAsStream("mybatis.xml");
            factory = new SqlSessionFactoryBuilder().build(is);
            threadLocal = new ThreadLocal<SqlSession>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSession(){
        //保证1.不同的请求，需要使用同一个Factory对象
        // 2.不同的请求，使用不同的SqlSession对象
        // 3.同一个请求，在责任链的任意位置获取的是同一个SqlSession对象，使用ThreadLocal对象
        SqlSession ss = threadLocal.get();
        if(ss==null){
            ss=factory.openSession();
            threadLocal.set(ss);
        }
        return ss;
    }
    //声明关闭的方法：
    public static void closeAll(){
        SqlSession ss = threadLocal.get();
        if(ss!=null){
            ss.close();
        }
        threadLocal.set(null);
    }
}
