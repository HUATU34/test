package com.zjuse.bankingsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjuse.bankingsystem.utils.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("property")
public class Property {
    @TableId(type= IdType.AUTO)
    private Long id;//资产ID
    private Long accountid;//账户ID
    private PropertyType type;//资产类型

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getAccountid() {return accountid;}
    public void setAccountid(Long accountid) {this.accountid = accountid;}
    public PropertyType getType() {return type;}
    public void setType(PropertyType type) {this.type = type;}
}
