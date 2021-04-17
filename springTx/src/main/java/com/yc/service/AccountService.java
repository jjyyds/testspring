package com.yc.service;

import com.yc.bean.Accounts;
import com.yc.bean.Oprecord;
import java.util.List;

public interface AccountService {
    //开户
    Integer openAccount(Accounts account,double money);

    //存钱
    Accounts deposite(Accounts account,double money,String optype,String transferid);

    //取钱
    Accounts withdraw(Accounts account,double money,String optype,String transferid);

    //转账
    Accounts transfer(Accounts inAccount,Accounts outAccount,double money);

    //查看余额
    Accounts showBalance(Accounts account);

    //查看日志
    List<Oprecord> findById(Accounts accounts);
}
