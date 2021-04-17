package com.yc.dao;

import com.yc.bean.Oprecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class OprecordDaoImpl implements OprecordDao{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
    }

    @Override
    public void saveOpRecord(Oprecord oprecord) {
        String sql="insert into oprecord values(null,?,?,?,?,?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update((connection)->{
            PreparedStatement pstmt=connection.prepareStatement(sql,new String[]{"id"});
            pstmt.setObject(1,oprecord.getAccountid());
            pstmt.setObject(2,oprecord.getOpmoney());
            pstmt.setObject(3,oprecord.getOptime());
            pstmt.setObject(4,oprecord.getOptype());
            pstmt.setObject(5,oprecord.getTransferid());
            return pstmt;
        },keyHolder);
    }

    @Override
    public List<Oprecord> findAll() {
        String sql="select * from oprecord";
        List<Oprecord> list=jdbcTemplate.query(sql,(rs,rowNum)->{
            Oprecord oprecord=new Oprecord();
            oprecord.setId((Integer) rs.getObject("id"));
            oprecord.setAccountid((Integer)rs.getObject("accountid"));
            oprecord.setOpmoney((Double)rs.getObject("opmoney"));
            oprecord.setOptype((String)rs.getObject("optype"));
            oprecord.setTransferid((String)rs.getObject("transferid"));
            oprecord.setOptime(rs.getTimestamp("optime"));
            return oprecord;
        });
        return list;
    }

    @Override
    public Oprecord findOprecord(int id) {
        String sql="select * from oprecord where id=?";
        Oprecord or=jdbcTemplate.queryForObject(sql,(rs,rowNum)->{
            Oprecord oprecord=new Oprecord();
            oprecord.setId((Integer) rs.getObject("id"));
            oprecord.setAccountid((Integer)rs.getObject("accountid"));
            oprecord.setOpmoney((Double)rs.getObject("opmoney"));
            oprecord.setOptype((String)rs.getObject("optype"));
            oprecord.setTransferid((String)rs.getObject("transferid"));
            oprecord.setOptime(rs.getTimestamp("optime"));
           return oprecord;
        },id);
        return or;
    }

    @Override
    public List<Oprecord> findByAccountid(int accountid) {
        String sql="select * from oprecord where accountid=?";
        List<Oprecord> list=jdbcTemplate.query(sql,(rs,rowNum)->{
            Oprecord oprecord=new Oprecord();
            oprecord.setId((Integer) rs.getObject("id"));
            oprecord.setAccountid((Integer)rs.getObject("accountid"));
            oprecord.setOpmoney((Double)rs.getObject("opmoney"));
            oprecord.setOptype((String)rs.getObject("optype"));
            oprecord.setTransferid((String)rs.getObject("transferid"));
            oprecord.setOptime(rs.getTimestamp("optime"));
            return oprecord;
        },accountid);
        return list;
    }
}
