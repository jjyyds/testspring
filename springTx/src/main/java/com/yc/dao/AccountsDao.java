package com.yc.dao;

import com.yc.bean.Accounts;

import java.util.List;

public interface AccountsDao {
    int saveAccount(Accounts account);

    Accounts updateAccount(Accounts account);

    Accounts findAccount(int id);

    List<Accounts> findAll();

    void delete(Accounts account);
}
