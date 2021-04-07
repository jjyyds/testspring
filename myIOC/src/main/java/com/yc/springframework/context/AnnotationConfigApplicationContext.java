package com.yc.springframework.context;

import com.yc.springframework.stereotype.*;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class AnnotationConfigApplicationContext implements ApplicationContext{

    private Map<String,Object> beanMap=new HashMap<>();
//    private Map<String,Class> classMap=new HashMap<>();//如果是懒加载，先存类元信息，调用时再实例化
    private Set<Class> managedBeanClasses=new HashSet<>();

    public AnnotationConfigApplicationContext(){

    }

    public AnnotationConfigApplicationContext(Class<?>... componentClasses){
        try {
            register(componentClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void register(Class<?>[] componentClasses) throws Exception {
        if(componentClasses==null || componentClasses.length<=0){
            throw new RuntimeException("没有指定配置类");
        }
        for (Class cl : componentClasses) {
            if(!cl.isAnnotationPresent(Configuration.class)){
                continue;
            }
            String basePackages[]=getAppConfigBasePackages(cl);
            if(cl.isAnnotationPresent(ComponentScan.class)){
                ComponentScan componentScan = (ComponentScan)cl.getAnnotation(ComponentScan.class);
                if(componentScan!=null && componentScan.classes().length>0){
                    basePackages=componentScan.classes();
                }
            }
            //处理@Bean的情况
            Object obj=cl.newInstance();//obj就是当前解析的 Config对象
            handleBean(cl,obj);
            //处理Config中包扫描路径的托管bean
            for (String basePackage : basePackages) {
                scanPackageAndSubPackageClasses(basePackage);
            }
            //继续处理其他的托管bean
            handleManagedBean();
            //DI
            handleDI();
        }
    }

    /**
     * 循环beanMap中的每个bean，找到每个类中的每个@Autowired和@Resource注解放方法以实现di
     */
    private void handleDI() throws InvocationTargetException, IllegalAccessException {
        Collection<Object> objectCollection=beanMap.values();
        for (Object o : objectCollection) {
            Class<?> cls = o.getClass();
            //遍历判断方法有无注解
            Method[] ms = cls.getDeclaredMethods();
            for (Method m : ms) {
                if(m.isAnnotationPresent(Autowired.class) && m.getName().startsWith("set")){
                    invokeMethodAutowired(m,o);
                }else if(m.isAnnotationPresent(Resource.class) && m.getName().startsWith("set")){
                    invokeMethodResource(m,o);
                }
            }
            //遍历判断字段有无注解
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(Autowired.class)){
                    invokeFieldAutowired(field,o);
                }else if(field.isAnnotationPresent(Resource.class)){
                    invokeFieldResource(field,o);
                }
            }
        }
    }

    private void invokeFieldResource(Field field, Object o) throws IllegalAccessException {
        String beanId=field.getAnnotation(Resource.class).name();
        if(null==beanId || "".equals(beanId)){
            beanId=field.getName();
        }
        Object obj=beanMap.get(beanId);
        field.setAccessible(true);
        field.set(o,obj);
    }

    private void invokeFieldAutowired(Field field, Object o) throws IllegalAccessException {
        //获取字段的类型
        Class<?> type = field.getType();
        //从beanMap中循环所有的Object
        Set<String> keys=beanMap.keySet();
        for (String key : keys) {
            Object obj=beanMap.get(key);
            //判断这些Object是否为参数类型的实例
            Class[] interfaces=obj.getClass().getInterfaces();
            if(null==interfaces || interfaces.length<=0){
                if(obj.getClass().getTypeName().equalsIgnoreCase(type.getName())){
                    field.setAccessible(true);
                    field.set(o,obj);
                }
            }else{
                for(Class c:interfaces){
                    if(c==type){
                        //激活
                        field.setAccessible(true);
                        field.set(o,obj);
                        break;
                    }
                }
            }
        }
    }

    private void invokeMethodResource(Method m, Object o) throws InvocationTargetException, IllegalAccessException {
        //取出resource中的name值
        Resource resource = m.getAnnotation(Resource.class);
        String beanId = resource.name();
        //如果没有，则取出m方法中参数的类型名，改成首字母小写，当成beanId
        if(null==beanId || "".equals(beanId)){
            String simpleName = m.getParameterTypes()[0].getSimpleName();
            beanId=simpleName.substring(0,1).toLowerCase()+simpleName.substring(1);
        }
        //从beanMap中取出
        Object obj = beanMap.get(beanId);
        //激活
        m.invoke(o,obj);
    }

    private void invokeMethodAutowired(Method m, Object o) throws InvocationTargetException, IllegalAccessException {
        //取出m的参数类型
        Class type = m.getParameterTypes()[0];
        //从beanMap中循环所有的Object
        Set<String> keys=beanMap.keySet();
        for (String key:keys) {
            Object obj = beanMap.get(key);
            //判断这些Object是否为参数类型的实例
            Class[] interfaces=obj.getClass().getInterfaces();
            if(null==interfaces || interfaces.length<=0){
                if(obj.getClass().getTypeName().equalsIgnoreCase(type.getName())){
                    m.invoke(o,obj);
                }
            }else{
                for(Class c:interfaces){
                    if(c==type){
                        //激活
                        m.invoke(o,obj);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 处理managedBeanClasses里的所有class类，
     * 筛选出所有的@Component @Service @Repository @Controller 存到beanMap中
     */
    private void handleManagedBean() throws Exception {
        for (Class c : managedBeanClasses) {
            if(c.isAnnotationPresent(Component.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(Repository.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(Service.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(Controller.class)){
                saveManagedBean(c);
            }
        }
    }

    private void saveManagedBean(Class c) throws Exception {
        Object o=c.newInstance();
        handlerPostConstruct(o,c);
        String beanId=c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        beanMap.put(beanId,o);
    }

    private void handleBean(Class cl,Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.获取cl中所有的method
        Method[] ms = cl.getDeclaredMethods();
        //2.循环判断每个method上是否有@Bean注解
        for (Method m : ms) {
            if(m.isAnnotationPresent(Bean.class)){
                //3.有，则invoke，它有返回值，将返回值存到beanmap，键是方法名，值是返回值对象
                Object o=m.invoke(obj,null);
                //加入处理@Bean注解对应的方法所实例化的类中的@PostConstruct对应的方法
                handlerPostConstruct(o,o.getClass());
                beanMap.put(m.getName(),o);
            }
        }
    }

    /**
     * 处理bean中的@PostConstruct对应的方法
     * @param o
     * @param cl
     */
    private void handlerPostConstruct(Object o, Class cl) throws InvocationTargetException, IllegalAccessException {
        Method[] ms = cl.getDeclaredMethods();
        for (Method m : ms) {
            if(m.isAnnotationPresent(PostConstruct.class)){
                m.invoke(o);
            }
        }
    }

    private String[] getAppConfigBasePackages(Class cl){
        String[] paths=new String[1];
        paths[0]=cl.getPackage().getName();
        return paths;
    }

    //根据包名查找包底下的类
    private void scanPackageAndSubPackageClasses(String path){
        ClassLoader classLoader=this.getClass().getClassLoader();
        URI uri=null;
        try {
            uri=classLoader.getResource(path.replace(".","/")).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        File file=new File(uri);
        file.listFiles(new FileFilter() {
            public boolean accept(File chiFile) {
                if(chiFile.isDirectory()){
                    scanPackageAndSubPackageClasses(path+"."+chiFile.getName());
                }
                if(chiFile.getName().endsWith(".class")){
                    Class<?> clazz = null;
                    try {
                        clazz = classLoader.loadClass(path + "." + chiFile.getName().replace(".class", ""));
                        managedBeanClasses.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
