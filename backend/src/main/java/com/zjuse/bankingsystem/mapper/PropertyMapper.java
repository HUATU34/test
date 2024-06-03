package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import com.zjuse.bankingsystem.entity.Property;

import java.util.List;

/*@Mapper
public interface PropertyMapper {
    @Select("select * from property where id = #{id}")
    List<Property> getPropertyById(@Param("id") Long id);

    @Select("select *  from property where accountid = #{accountif}")
    List<Property> getPropertyByAccountId(@Param("accountif") Long accountif);

    @Select("select * from property where accountid = #{accountid} and type = #{type}")
    List<Property>getPropertyByAccountAndType(@Param("accountid") Long accountid, @Param("type") int type);

    @Update("insert into property(accountid, type) values (#{accountid},#{type})")
    void insertProperty(@Param("accountid") Long accountid, @Param("type") int type);

    @Select("select * from property where accountid = #{accountid} and type = #{type}  order by id desc")
    List<Property>getNewPropertyId(@Param("accountid") Long accountid, @Param("type") int type);

    @Delete("delete from property where id = #{id}")
    void deletePropertyById(@Param("id") Long id);
}*/

public interface PropertyMapper extends BaseMapper<Property> {

}
