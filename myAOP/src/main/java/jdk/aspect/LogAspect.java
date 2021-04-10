package jdk.aspect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LogAspect implements InvocationHandler {

    private Object target;//目标类对象

    public LogAspect(Object target){
        this.target=target;
    }

    public Object createProxy(){
        //新建一个代理实例
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),
                this.target.getClass().getInterfaces(),this);
    }


    /**
     * 回调方法，当jvm调用代理对象的被代理的方法时，会由jvm自动调用此方法
     * @param proxy 代理类的对象
     * @param method 目标类的方法
     * @param args  方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //前置增强
        System.out.println("前置");
        Object reVal = method.invoke(this.target, args);
        //后置增强
        System.out.println("后置");
        return reVal;
    }
}
