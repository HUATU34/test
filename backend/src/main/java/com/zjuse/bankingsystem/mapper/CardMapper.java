package com.zjuse.bankingsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjuse.bankingsystem.entity.Card;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*@Mapper
public interface CardMapper {
    @Select("select * from card where id = #{id}")
    public List<Card> getCardById(Long id);

    @Select("select * from card where accountid = #{accountid}")
    public List<Card> getCardByAccountId(Long accountid);

    @Update("insert into card(type, accountid) values (#{type},#{accountid})")
    public void insertCard(@Param("type") int type, @Param("accountid")Long accountid);

    @Delete("delete from card where id = #{id}")
    public void deleteCardById(Long id);
}*/

public interface CardMapper extends BaseMapper<Card>{

}