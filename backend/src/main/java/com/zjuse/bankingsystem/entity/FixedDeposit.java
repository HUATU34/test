package com.zjuse.bankingsystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("fixed_deposit")
public class FixedDeposit {
    private Long propertyid;//定期资产ID
    Long accountid;//账户ID
    BigDecimal amount;//金额
    Long date;//存款日期
    int length;//存款时长
    boolean autocontinue;//是否自动续期
    BigDecimal interestrate;//存款时利率

    public Long getPropertyid() {return propertyid;}
    public void setPropertyid(Long propertyid) {this.propertyid = propertyid;}
    public Long getAccountid() {return accountid;}
    public void setAccountid(Long accountid) {this.accountid = accountid;}
    public BigDecimal getAmount() {return amount;}
    public void setAmount(BigDecimal amount) {this.amount = amount;}
    public Long getDate() {return date;}
    public void setDate(Long date) {this.date = date;}
    public int getLength() {return length;}
    public void setLength(int length) {this.length = length;}
    public boolean isAutocontinue() {return autocontinue;}
    public void setAutocontinue(boolean autocontinue) {this.autocontinue = autocontinue;}
    public BigDecimal getInterestRate() {return interestrate;}
    public void setInterestRate(BigDecimal interestRate) {this.interestrate = interestRate;}
}
