package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjuse.bankingsystem.entity.FixedDeposit;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/*@Mapper
public interface FixedDepositMapper {
    @Update("insert into fixed_deposit values(#{propertyid}, #{accountid}, #{amount}, #{date}, #{length}, #{interestrate}, #{autocontinue})")
    void insertFixedDeposit(@Param("propertyid")Long propertyid, @Param("accountid")Long accountid, @Param("amount")BigDecimal amount,@Param("date")Long date,@Param("length")int length,@Param("interest")BigDecimal interest,@Param("autocontinue")boolean autocontinue);

    @Select("select * from fixed_deposit where accountid=#{accountid}")
    List<FixedDeposit> getFixedDeposit(@Param("accountid")Long accountid);

    @Select("select * from fixed_deposit where propertyid = #{propertyid}")
    List<FixedDeposit> getFixedDepositById(@Param("propertyid")Long propertyid);

    @Update("update fixed_deposit set amount = #{amount}, date = #{date}, interestrate = #{interestrate} where propertyid = #{propertyid}")
    void AutoContinue(@Param("amount")BigDecimal amount,@Param("date")Long date,@Param("interest")BigDecimal interest);

    @Delete("delete from fixed_deposit where propertyid = #{propertyid}")
    void deleteFixedDeposit(@Param("propertyid")Long propertyid);
}*/

public interface FixedDepositMapper extends BaseMapper<FixedDeposit> {

}
