package com.yc.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private Integer code;
    private Object data;
    private String msg;
}
