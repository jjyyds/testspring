package com.yc.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class Accounts {
    @ApiModelProperty(value = "accountId")
    private Integer accountId;
    @ApiModelProperty(value = "balance")
    private Double balance;
}
