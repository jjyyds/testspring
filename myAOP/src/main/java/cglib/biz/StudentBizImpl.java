package cglib.biz;

public class StudentBizImpl {
    public int add(String name) {
        System.out.println("调用add");
        return 100;
    }

    public void update(String name) {
        System.out.println("调用update");
    }

    public String find(String name) {
        System.out.println("调用find");
        return "名称"+name;
    }
}
