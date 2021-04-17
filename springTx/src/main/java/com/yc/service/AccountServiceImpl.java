package com.yc.service;

import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import com.yc.bean.Oprecord;
import com.yc.dao.AccountsDao;
import com.yc.dao.OprecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,
        readOnly = false,timeout = 100,rollbackForClassName = {"RuntimeException"})
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountsDao accountsDao;

    @Autowired
    private OprecordDao oprecordDao;

    @Override
    public Integer openAccount(Accounts account, double money) {
        account.setBalance(money);
        int accountid=accountsDao.saveAccount(account);
        //开户日志
        Oprecord oprecord=new Oprecord(null,accountid,money
                ,new Timestamp(System.currentTimeMillis()),OpTypes.deposite.getName(),"");
        oprecordDao.saveOpRecord(oprecord);
        return accountid;
    }

    @Override
    public Accounts deposite(Accounts account, double money,String optype,String transferid) {
        Accounts a=this.showBalance(account);
        Oprecord oprecord=new Oprecord(null,a.getAccountId(),money,
                new Timestamp(System.currentTimeMillis()),optype);
        if(transferid==null){
            oprecord.setTransferid("");
        }else{
            oprecord.setTransferid(transferid);
        }
        oprecordDao.saveOpRecord(oprecord);
        a.setBalance(a.getBalance()+money);
        accountsDao.updateAccount(a);
        return a;
    }

    @Override
    public Accounts withdraw(Accounts account, double money,String optype,String transferid) {
        Accounts a=this.showBalance(account);
        Oprecord oprecord=new Oprecord(null,a.getAccountId(),money,
                new Timestamp(System.currentTimeMillis()),optype);
        if(transferid==null){
            oprecord.setTransferid("");
        }else{
            oprecord.setTransferid(transferid);
        }
        oprecordDao.saveOpRecord(oprecord);
        a.setBalance(a.getBalance()-money);
        accountsDao.updateAccount(a);
        return a;
    }

    @Override
    public Accounts transfer(Accounts inAccount, Accounts outAccount, double money) {
        String uid= UUID.randomUUID().toString();
        this.deposite(inAccount,money,OpTypes.transfer.getName(),uid);
        Accounts newAccounts=this.withdraw(outAccount,money,OpTypes.transfer.getName(),uid);
        return newAccounts;
    }

    @Override
    @Transactional(readOnly = true)
    public Accounts showBalance(Accounts account) {
        return accountsDao.findAccount(account.getAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Oprecord> findById(Accounts accounts) {
        return oprecordDao.findByAccountid(accounts.getAccountId());
    }
}
