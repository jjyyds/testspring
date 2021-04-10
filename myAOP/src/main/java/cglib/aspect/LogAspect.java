package cglib.aspect;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class LogAspect implements MethodInterceptor {

    private Object target;//目标类对象

    public LogAspect(Object target){
        this.target=target;
    }

    public Object createProxy(){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     *
     * @param o 代理类对象
     * @param method  目标方法
     * @param args 方法参数
     * @param methodProxy 要代理的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("前置");
        Object reVal = method.invoke(this.target, args);
        System.out.println("后置");
        return reVal;
    }
}
