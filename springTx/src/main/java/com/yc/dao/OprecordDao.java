package com.yc.dao;

import com.yc.bean.Oprecord;
import java.util.List;

public interface OprecordDao {
    void saveOpRecord(Oprecord oprecord);

    List<Oprecord> findAll();

    Oprecord findOprecord(int id);

    List<Oprecord> findByAccountid(int accountid);
}
