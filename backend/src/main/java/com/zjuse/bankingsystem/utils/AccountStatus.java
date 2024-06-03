package com.zjuse.bankingsystem.utils;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountStatus {
    Normal(1),
    Frozen(2),
    Lost(3);

    @EnumValue
    Integer value;

    public int getvalue(){
        return value;
    }
}