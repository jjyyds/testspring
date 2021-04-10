package com.yc.biz;

import com.yc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service//给spring的类托管
public class StudentBizImpl implements StudentBiz{
    private StudentDao studentDao;

    public StudentBizImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public StudentBizImpl() {
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    @Autowired
    @Qualifier("studentDaoMybatisImpl")
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public int add(String name){
        int result = studentDao.add(name);
        return result;
    }

    public void update(String name){
        studentDao.update(name);
    }
}
