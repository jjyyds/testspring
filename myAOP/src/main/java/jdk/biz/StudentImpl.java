package jdk.biz;

public class StudentImpl implements StudentBiz{
    @Override
    public int add(String name) {
        System.out.println("调用add");
        return 100;
    }

    @Override
    public void update(String name) {
        System.out.println("调用update");
    }

    @Override
    public String find(String name) {
        System.out.println("调用find");
        return "名称"+name;
    }
}
