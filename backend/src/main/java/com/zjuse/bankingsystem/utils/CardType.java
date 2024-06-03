package com.zjuse.bankingsystem.utils;

import com.baomidou.mybatisplus.annotation.EnumValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CardType {
    Card(1),
    Book(2);

    @EnumValue
    Integer value;
     public int getvalue(){
         return value;
     }
}
