package com.yc.dao;

import com.yc.bean.Accounts;
import com.yc.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestAccountsDao {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        Assert.assertNotNull(dataSource);
        System.out.println(dataSource.getConnection());
    }

    @Autowired
    private AccountsDao accountsDao;

    @Test
    public void testAccountDaoImpl(){
        Assert.assertNotNull(accountsDao);
    }

    @Test
    public void testOpenAccount(){
        int id=accountsDao.saveAccount(new Accounts(null,100.00));
        System.out.println("新开户："+id);
    }

    @Test
    public void testFindAll(){
        List<Accounts> list = accountsDao.findAll();
        System.out.println(list);
    }

    @Test
    public void testFindAccount(){
        Accounts account= accountsDao.findAccount(1);
        System.out.println(account);
    }

    @Test
    public void testDelete(){
        accountsDao.delete(new Accounts(1,null));
    }
}
