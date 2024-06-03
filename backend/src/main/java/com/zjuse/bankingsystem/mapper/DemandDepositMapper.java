package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjuse.bankingsystem.entity.DemandDeposit;

import java.math.BigDecimal;
import java.util.List;

/*@Mapper
public interface DemandDepositMapper {
    @Select("select * from demand_deposit where propertyid = #{propertyid}")
    List<DemandDeposit> selectByPropertyId(@Param("propertyid") Long propertyid);

    @Select("select * from demand_deposit where accountid = #{accountid}")
    List<DemandDeposit> selectByAccountId(@Param("accountid") Long accountid);

    @Update("update demand_deposit set date = #{date},base = #{base} where propertyid = #{propertyid}")
    void updateDateAndBase(@Param("propertyid") Long propertyid, @Param("date") Long date, BigDecimal base);

    @Update("update demand_deposit set amount = amount + #{number} where propertyid = #{propertyid}")
    void updateAmount(@Param("propertyid") Long propertyid, BigDecimal number);

    @Update("insert into demand_deposit(propertyid, accountid, amount, date) value (#{propertyid},#{accountid},#{amount},#{date})")
    void insertDeposit(@Param("propertyid")Long propertyid,@Param("accouontid")Long accouontid,@Param("amount")BigDecimal amount);

    @Delete("delete from demand_deposit where propertyid = #{id}")
    void deleteByPropertyId(@Param("id") Long id);

    @Delete("delete from demand_deposit where accountid = #{id}")
    void deleteByAccountId(@Param("id") Long id);
}*/

public interface DemandDepositMapper extends BaseMapper<DemandDeposit> {

}
