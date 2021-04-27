package com.yc.springboot.controller;

import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import com.yc.bean.Result;
import com.yc.service.AccountService;
import com.yc.vo.AccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "银行账户操作接口",tags = {"账户操作接口"})
@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountsController {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value="查询账户余额", notes="查询账户余额", produces="application/json")
    @ApiImplicitParam(name = "accountid",value = "账户编号",required = true)
    @GetMapping("/showBalance/{accountid}")
    public @ResponseBody Result showBalance(@PathVariable("accountid") Integer accountid){
        log.info("accountid:"+accountid+"--查询余额");
        Result r=new Result();
        try{
            Accounts a=new Accounts();
            a.setAccountId(accountid);
            Accounts accounts = accountService.showBalance(a);
            r.setCode(1);
            r.setData(accounts);
        }catch (Exception e){
            e.printStackTrace();
            r.setCode(0);
            r.setMsg(e.getMessage());
        }
        return r;
    }

    @ApiOperation(value="账户开户", notes="根据金额开户，返回开户后的账户", produces="application/json")
    @ApiImplicitParam(name = "money",value = "开户金额",required = true)
    @RequestMapping(value = "/openAccount",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody Result openAccount(AccountVO accountVO){
        log.debug("用户请求开户"+accountVO.getMoney());
        Result r=new Result();
        try {
            Accounts a=new Accounts();
            double money=1;
            if(accountVO.getMoney()!=null || accountVO.getMoney()>0){
                money=accountVO.getMoney();
            }
            Integer id=accountService.openAccount(a,money);
            a.setAccountId(id);
            a.setBalance(money);
            r.setCode(1);
            r.setData(a);
        }catch (Exception e){
            e.printStackTrace();
            r.setCode(0);
            r.setMsg(e.getMessage());
        }
        return r;
    }

    @ApiOperation(value = "存款",notes = "根据账号，存款金额发出存款操作，返回操作完成后的新的余额")
    @RequestMapping(value = "/deposit",method = RequestMethod.POST)
    public Result deposit(AccountVO accountVO){
        Result r=new Result();
        Accounts a=new Accounts();
        a.setAccountId(accountVO.getAccountid());
        a.setBalance(accountVO.getMoney());
        try {
            Accounts account = accountService.deposite(a, a.getBalance(), OpTypes.deposite.getName(), null);
            r.setCode(1);
            r.setData(account);
        }catch (Exception e){
            e.printStackTrace();
            r.setCode(0);
            r.setMsg(e.getMessage());
        }
        return r;
    }

    @ApiOperation(value = "转账",notes = "根据账户编号及金额，对方账号来完成转账操作")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountid", value = "自己账户编号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "money", value = "转账金额", required = true, dataType = "double"),
            @ApiImplicitParam(name = "inAccountid", value = "对方接收账号", required = true, dataType = "int")})
    @RequestMapping(value = "/transfer",method = RequestMethod.POST)
    public Result transfer(AccountVO accountVO){
        Accounts inAccount=new Accounts();
        inAccount.setAccountId(accountVO.getInAccountid());
        Accounts outAccount=new Accounts();
        outAccount.setAccountId(accountVO.getAccountid());
        Result r=new Result();
        try{
            Accounts a=accountService.transfer(inAccount,outAccount,accountVO.getMoney());
            r.setCode(1);
            r.setData(a);
        }catch (Exception e){
            e.printStackTrace();
            r.setData(0);
            r.setMsg(r.getMsg());
        }
        return r;
    }

    @ApiOperation(value = "取款", notes = "根据账户编号及金额来完成取款操作，注意此时的金额表示要取的金额")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountid", value = "账户编号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "money", value = "操作金额", required = true, dataType = "double")})
    @RequestMapping(value = "/withdraw",method = RequestMethod.POST)
    public Result withDraw(AccountVO accountVO){
        Result r=new Result();
        Accounts a = new Accounts();
        a.setAccountId(accountVO.getAccountid());
        try{
            a=accountService.withdraw(a, accountVO.getMoney(), OpTypes.withdraw.getName(), "");
            r.setCode(1);
            r.setData(a);
        }catch (Exception e){
            e.printStackTrace();
            r.setCode(0);
            r.setMsg(e.getMessage());
        }
        return r;
    }
}
