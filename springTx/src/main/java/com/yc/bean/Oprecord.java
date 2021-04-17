package com.yc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oprecord {
    private Integer id;
    private Integer accountid;
    private Double opmoney;
    private Timestamp optime;
    private String optype;
    private String transferid;

    public Oprecord(Integer id, Integer accountId, double money, Timestamp optime, String optype) {
        this.id=id;
        this.accountid=accountId;
        this.opmoney=money;
        this.optime=optime;
        this.optype=optype;
    }
}
