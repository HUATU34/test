package com.zjuse.bankingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("demand_deposit")
public class DemandDeposit {
    private Long propertyid;//资产ID
    Long accountid;//账户ID
    BigDecimal amount;//余额
    Long date;//上次资产变更日期
    BigDecimal base;//基数

    public Long getpropertyid(){return propertyid;}
    public void setpropertyid(Long property_id){this.propertyid = property_id;}
    public Long getaccountid(){return accountid;}
    public void setaccountid(Long accountid){this.accountid = accountid;}
    public BigDecimal getamount(){return amount;}
    public void setamount(BigDecimal amount){this.amount = amount;}
    public Long getdate(){return date;}
    public void setdate(Long date){this.date = date;}
    public BigDecimal getbase(){return base;}
    public void setbase(BigDecimal base){this.base = base;}
}
