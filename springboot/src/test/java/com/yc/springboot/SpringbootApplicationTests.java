package com.yc.springboot;

import com.yc.bean.Accounts;
import com.yc.bean.OpTypes;
import com.yc.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Autowired
	private AccountService accountService;

	@Test
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
		out.setAccountId(1);
		Accounts in=new Accounts();
		in.setAccountId(2);
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
