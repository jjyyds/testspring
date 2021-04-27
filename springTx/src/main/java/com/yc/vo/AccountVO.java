package com.yc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AccountVO implements Serializable {
    @ApiModelProperty(value = "账号")
    private Integer accountid;
    @ApiModelProperty(value = "金额")
    private Double money;
    @ApiModelProperty(value = "对方账号")
    private Integer inAccountid;
}
