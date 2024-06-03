package com.zjuse.bankingsystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.zjuse.bankingsystem.utils.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@TableName("account")
public class Account {
    @TableId(type = IdType.AUTO)
    private Long id;//账户ID
    private  String name;//姓名
    @JsonProperty("phonenumber")
    private String phonenumber;//电话号
    private AccountStatus status;//状态
    @JsonProperty("card_id")
    private Long cardId;//绑定的卡的ID
    @JsonIgnore
    private String password;//密码
    @JsonIgnore
    private String salt;//盐
    private String citizenid;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPhonenumber() {return phonenumber;}
    public void setPhonenumber(String phonenumber) {this.phonenumber = phonenumber;}
    public AccountStatus getStatus() {return status;}
    public void setStatus(AccountStatus status) {this.status = status;}
    public Long getCardId() {return cardId;}
    public void setCardId(Long cardId) {this.cardId = cardId;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getSalt() {return salt;}
    public void setSalt(String salt) {this.salt = salt;}
    public String getCitizenid() {return citizenid;}
    public void setCitizenid(String citizenid) {this.citizenid = citizenid;}
}

