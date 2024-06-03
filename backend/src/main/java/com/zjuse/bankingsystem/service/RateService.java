package com.zjuse.bankingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjuse.bankingsystem.entity.Rate;
import com.zjuse.bankingsystem.mapper.RateMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RateService {
    @Autowired
    RateMapper rateMapper;
    public ApiResult getrate(){
        try{
            //创建实例
            QueryWrapper wrapper = new QueryWrapper<>();
            Rate rate = rateMapper.selectOne(wrapper);
            if(rate ==null) {
                return new ApiResult(false, "未查询到利率");
            }else
                return new ApiResult(true,rate);
        }catch (Exception e){
            return  new ApiResult(false,e.getMessage());
        }
    }
}
