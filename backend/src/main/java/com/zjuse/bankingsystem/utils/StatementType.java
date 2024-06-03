package com.zjuse.bankingsystem.utils;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatementType {
    DemandDeposit(1),//活期存入
    DemandDraw(2),//活期取出
    DemandInterest(3),//活期利息
    TransferIn(4),//转入
    TransferOut(5),//转出
    FixedDeposit(6),//定期存入
    FixedInterest(7),//定期转存
    FixedDraw(8);//定期取款

    @EnumValue
    Integer value;
}
