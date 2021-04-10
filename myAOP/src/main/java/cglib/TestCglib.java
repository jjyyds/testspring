package cglib;

import cglib.aspect.LogAspect;
import cglib.biz.StudentBizImpl;

public class TestCglib {
    public static void main(String[] args) {
        StudentBizImpl sbi=new StudentBizImpl();
        LogAspect la=new LogAspect(sbi);
        StudentBizImpl proxy = (StudentBizImpl)la.createProxy();
        proxy.add("张三");
    }
}
