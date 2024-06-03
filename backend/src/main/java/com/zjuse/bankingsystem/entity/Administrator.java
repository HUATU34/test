package com.zjuse.bankingsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@TableName("administrator")
@RequiredArgsConstructor
@AllArgsConstructor
public class Administrator {
    @TableId(type = IdType.AUTO)
    private long id;//管理员ID
    @JsonIgnore
    private String password;//密码
    private String username;//用户名
    @JsonIgnore
    private String salt;//盐

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getSalt() {return salt;}
    public void setSalt(String salt) {this.salt = salt;}
}
