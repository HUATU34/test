package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjuse.bankingsystem.entity.Statement;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

/*@Mapper
public interface StatementMapper {
    @Update("insert into statement value(#{accountid}, #{amount}, #{date}, #{type}, #{traced}, #{operatorid}) ")
    void insertStatement(@Param("accountid")Long accountid, @Param("amount")BigDecimal amount, @Param("date")Long date, @Param("type")int type, @Param("traced")Long traced, @Param("operatorid")Long operatorid);

    @Select("select * from statement order by accountid asc ")
    List<Statement> getAllStatement();

    @Select("select * from statement where accountid = #{accountid}")
    List<Statement> getStatementbyAccountid(Long accountid);
}*/

public interface StatementMapper extends BaseMapper<Statement> {

}
