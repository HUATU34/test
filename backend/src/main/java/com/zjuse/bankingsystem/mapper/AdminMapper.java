package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjuse.bankingsystem.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*@Mapper
public interface AdminMapper {
    @Select("select * from administrator where id = #{id}")
    public List<Administrator> getAdministratorById(Long id);

    @Update("insert into administrator (`username`,`password`,`salt`)values (#{username},#{password},#{salt})")
    public void addAdministrator(@Param("username")String username,  @Param("password")String password, @Param("salt")String salt);

    @Select("select * from administrator where username = #{username} and password = #{password} and salt=#{salt}")
    public List<Administrator>getAdministratorByInfo(@Param("username")String username, @Param("password")String password , @Param("salt")String salt);

    @Delete("delete from administrator where id = #{id}")
    public void deleteAdministratorById(Long id);

    @Update("update administrator set username = #{username}, password = #{password}, salt = #{salt} where id = #{id}")
    public void updateAdministrator(@Param("id")Long id, @Param("username")String username, @Param("password")String password, @Param("salt")String salt);
}*/

public interface AdminMapper extends BaseMapper<Administrator> {

}