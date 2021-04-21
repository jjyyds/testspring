package com.yc.springboot.controller;

import com.google.gson.Gson;
import com.yc.bean.Accounts;
import com.yc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/showBalance/{accountid}")
    public String showBalance(@PathVariable("accountid") Integer accountid){
        Accounts a=new Accounts();
        a.setAccountId(accountid);
        Accounts accounts = accountService.showBalance(a);
        return new Gson().toJson(accounts);
    }

}
