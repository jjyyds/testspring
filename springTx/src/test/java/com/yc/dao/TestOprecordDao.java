package com.yc.dao;

import com.yc.bean.OpTypes;
import com.yc.bean.Oprecord;
import com.yc.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestOprecordDao {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        Assert.assertNotNull(dataSource);
        System.out.println(dataSource.getConnection());
    }

    @Autowired
    private OprecordDao oprecordDao;

    @Test
    public void testSaveOpRecord(){
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        oprecordDao.saveOpRecord(new Oprecord(null,1,1.0,timestamp, OpTypes.deposite.getName(), ""));
    }

    @Test
    public void testFindOprecord(){
        System.out.println(oprecordDao.findOprecord(3));
    }

    @Test
    public void testFindAll(){
        List<Oprecord> list=oprecordDao.findAll();
        System.out.println(list);
    }

    @Test
    public void testFindByAccountid(){
        List<Oprecord> list=oprecordDao.findByAccountid(1);
        System.out.println(list);
    }
}
