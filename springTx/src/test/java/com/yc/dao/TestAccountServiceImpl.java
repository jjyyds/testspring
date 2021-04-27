package com.yc.dao;

import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import com.yc.config.AppConfig;
import com.yc.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestAccountServiceImpl {
    @Autowired
    private AccountService accountService;

    @Test
    @Transactional //在junit中使用，用于恢复线程，回滚
    public void testOpenAccount(){
        Integer accountid=accountService.openAccount(new Accounts(),1);
        System.out.println(accountid);
        Assert.assertNotNull(accountid);
    }

    @Test
    public void testDeposite(){
        Accounts a=new Accounts();
        a.setAccountId(1);
        Accounts aa=accountService.deposite(a,100, OpTypes.deposite.getName(), null);
        System.out.println(aa);
    }

    @Test
    public void testWithdraw(){
        Accounts a=new Accounts();
        a.setAccountId(1);
        Accounts aa=accountService.withdraw(a,100, OpTypes.withdraw.getName(), null);
        System.out.println(aa);
    }

    @Test
    public void testTransfer(){
        Accounts out=new Accounts();
        out.setAccountId(2);
        Accounts in=new Accounts();
        in.setAccountId(1);
        this.accountService.transfer(in,out,5);
    }

    @Test
    public void testShowBalance(){
        Accounts a=new Accounts();
        a.setAccountId(1);
        a=this.accountService.showBalance(a);
        System.out.println(a);
    }
}
