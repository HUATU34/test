package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjuse.bankingsystem.entity.Administrator;
import com.zjuse.bankingsystem.entity.Cashier;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*@Mapper
public interface CashierMapper {
    @Select("select * from cashier where id = #{id}")
    public List<Cashier> getCashierById(Long id);

    @Update("insert into cashier (`username`,`password`,`salt`)values (#{username},#{password},#{salt})")
    public void addCashier(@Param("username")String username, @Param("password")String password, @Param("salt")String salt);

    @Select("select * from cashier where username = #{username} and password = #{password} and salt=#{salt}")
    public List<Cashier>getCashierByInfo(@Param("username")String username, @Param("password")String password , @Param("salt")String salt);

    @Delete("delete from cashier where id = #{id}")
    public void deleteCashierById(Long id);

    @Update("update cashier set username = #{username}, password = #{password}, salt = #{salt} where id = #{id}")
    public void updateCashier(@Param("id")Long id, @Param("username")String username, @Param("password")String password, @Param("salt")String salt);
}*/

public interface CashierMapper extends BaseMapper<Cashier> {

}
