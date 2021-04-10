package jdk;

import jdk.aspect.LogAspect;
import jdk.biz.StudentBiz;
import jdk.biz.StudentImpl;

public class TestJDK {
    public static void main(String[] args) {
        StudentBiz target=new StudentImpl();
        LogAspect la=new LogAspect(target);
        StudentBiz proxy = (StudentBiz)la.createProxy();
        proxy.add("张三");
        proxy.update("张三");
    }
}
