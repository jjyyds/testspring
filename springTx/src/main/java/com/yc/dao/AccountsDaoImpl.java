package com.yc.dao;

import com.yc.bean.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AccountsDaoImpl implements AccountsDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public int saveAccount(Accounts account) {
        String sql="insert into accounts(balance) values(?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();//生成键的保存器
        jdbcTemplate.update((connection)->{
            PreparedStatement pstmt = connection.prepareStatement(sql,new String[]{"accountid"});
            pstmt.setDouble(1,account.getBalance());
            return pstmt;
        },keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Accounts updateAccount(Accounts account) {
        String sql="update accounts set balance=? where accountid=?";
        jdbcTemplate.update(sql,account.getBalance(),account.getAccountId());
        return account;
    }

    @Override
    public Accounts findAccount(int id) {
        String sql="select * from accounts where accountid=?";
        Accounts account=jdbcTemplate.queryForObject(sql,(rs,rowNum)->{
            Accounts a=new Accounts(rs.getInt("accountid"),rs.getDouble("balance"));
            return a;
        },id);
        return account;
    }

    @Override
    public List<Accounts> findAll() {
        String sql="select * from accounts";
        List<Accounts> list=jdbcTemplate.query(sql,(rs,rowNum)->{
           Accounts account=new Accounts(rs.getInt("accountid"),rs.getDouble("balance"));
           return account;
        });
        return list;
    }

    @Override
    public void delete(Accounts account) {
        String sql="delete from accounts where accountid=?";
        jdbcTemplate.update(sql,account.getAccountId());
    }
}
