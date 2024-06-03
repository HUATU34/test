package com.zjuse.bankingsystem.mapper;

import com.zjuse.bankingsystem.entity.Account;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*@Mapper
public interface AccountMapper {
    @Select("select * from account where id = #{id}")
    public List<Account> getAccountById(Long id);

    @Update("insert into account (name, phonenumber, citizenid , card_id, password, salt) values(#{name},#{phonenumber},#{citizenid},#{card_id},#{password},#{salt})")
    public void insertAccount(@Param("name")String name,@Param("phonenumber")String phonenumber,@Param("citizenid")String citizenid,@Param("card_id")Long card_id,@Param("password")String password,@Param("salt")String salt);

    @Select("select * from account where citizenid = #{citizenid} and salt = #{salt} and password = #{password}")
    public List<Account> getAccountByInfo(@Param("citizenid")String citizenid,@Param("salt")String salt,@Param("password")String password);

    @Update("update account set password = #{password}, salt = #{salt} where id = #{id}")
    public void ChangePassword(@Param("password")String password,@Param("salt")String salt,@Param("id")Long id);

    @Update("update account set status = #{status} where id = #{id}")
    public void changeStatus(@Param("status")int status,@Param("id")Long id);

    @Update("update account set card_id = #{cardid} where id = #{id}")
    public void changeCardId(@Param("cardid")Long cardid,@Param("id")Long id);

    @Update("update account set phonenumber = #{phonenumber} where id = #{id}")
    public void changePhonenumber(@Param("id")Long id,@Param("phonenumber") String phonenumber);

    @Select("select * from account where name = #{name} and citizenid = #{citizenid}")
    public List<Account> getAccountByPersion(@Param("name")String name,@Param("citizenid")String citizenid);

    @Select("select * from account where card_id = #{card_id}")
    public List<Account> getAccountByCardId(@Param("card_id")Long card_id);

    @Delete("delete from account where id = #{id}")
    public void deleteAccountById(@Param("id")Long id);
}*/


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface AccountMapper extends BaseMapper<Account> {

}
