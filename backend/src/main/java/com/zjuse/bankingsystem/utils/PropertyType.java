package com.zjuse.bankingsystem.utils;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PropertyType {
    demand(1),
    fixed(2);

    @EnumValue
    Integer value;

    public int getvalue(){
        return value;
    }
}
