package com.zjuse.bankingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zjuse.bankingsystem.utils.StatementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("statement")
public class Statement {
    Long accountid;//账户ID
    BigDecimal amount;//金额
    Long date;//日期
    StatementType type;//类型
    Long traced;//来源/去向

    public Long getaccountid(){return accountid;}
    public void setaccountid(Long accountid){this.accountid = accountid;}
    public BigDecimal getamount(){return amount;}
    public void setamount(BigDecimal amount){this.amount = amount;}
    public Long getdate(){return date;}
    public void setdate(Long date){this.date = date;}
    public StatementType getType(){return type;}
    public void setType(StatementType type){this.type = type;}
    public Long gettraced(){return traced;}
    public void settraced(Long traced){this.traced = traced;}
}
