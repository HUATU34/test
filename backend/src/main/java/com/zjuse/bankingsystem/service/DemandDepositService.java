package com.zjuse.bankingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjuse.bankingsystem.entity.*;
import com.zjuse.bankingsystem.mapper.AccountMapper;
import com.zjuse.bankingsystem.mapper.DemandDepositMapper;
import com.zjuse.bankingsystem.mapper.RateMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.CardType;
import com.zjuse.bankingsystem.utils.StatementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DemandDepositService {
    //余额变动、基数结算，利息结算
    @Autowired
    private StatementService statementService;
    @Autowired
    DemandDepositMapper depositMapper;
    RateMapper rateMapper;
    public ApiResult newDemandDeposit(Long account_id, Long propertyid){
        try{
            //创建活期存款实例
            DemandDeposit deposit = new DemandDeposit();
            deposit.setaccountid(account_id);
            deposit.setamount(BigDecimal.valueOf(0));
            deposit.setbase(BigDecimal.valueOf(0));
            deposit.setpropertyid(propertyid);
            deposit.setdate(System.currentTimeMillis());
            //插入活期存款并获取id
            depositMapper.insert(deposit);
            return new ApiResult(true,deposit);
        }catch (Exception e){
            return  new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult DeleteDemandDepositByAccountId(Long account_id){
        try{
            //获得deposit
            DemandDeposit deposit = depositMapper.selectById(account_id);
            Long pid = deposit.getpropertyid();
            //余额不为0
            if(!deposit.getamount().equals(BigDecimal.valueOf(0))) {
                return new ApiResult(false,"仍有余额");
            }
            //删除
            QueryWrapper deposit_wrapper = new QueryWrapper<>();
            deposit_wrapper.eq("id",pid);
            depositMapper.delete(deposit_wrapper);
            return new ApiResult(true,"删除成功");
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult changeamount(Long account_id,BigDecimal amount){
        try{
            //获得实例
            QueryWrapper deposit_wrapper = new QueryWrapper<>();
            deposit_wrapper.eq("accountid",account_id);
            DemandDeposit deposit = depositMapper.selectOne(deposit_wrapper);
            BigDecimal oldamount = deposit.getamount();
            //如果值小于0，为取款等操作
            if(amount.compareTo(BigDecimal.valueOf(0)) == -1){
                //取款的金额大小
                BigDecimal re_amount = amount.negate();
                //比余额大
                if(re_amount.compareTo(oldamount) == 1){
                    return new ApiResult(false,"余额不足");
                }else{
                    //更新数据库
                    amount=oldamount.subtract(re_amount);
                    UpdateWrapper<DemandDeposit> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("accountid", account_id);
                    updateWrapper.set("amount",amount);
                    depositMapper.update(null,updateWrapper);
                    //更新基数
                    ApiResult baseResult= settlebase(account_id,oldamount);
                    if(!baseResult.ok){
                        return new ApiResult(false,baseResult.message);
                    }
                    return new ApiResult(true,deposit);
                }
            }else{
                //更新数据库
                amount=oldamount.add(amount);
                UpdateWrapper<DemandDeposit> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("accountid", account_id);
                updateWrapper.set("amount",amount);
                depositMapper.update(null,updateWrapper);
                //更新基数
                ApiResult baseResult= settlebase(account_id,oldamount);
                if(!baseResult.ok){
                    return new ApiResult(false,baseResult.message);
                }
                return new ApiResult(true,deposit);
            }
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult settlebase(Long account_id,BigDecimal old_amount){
        try{
            //获得实例
            QueryWrapper deposit_wrapper = new QueryWrapper<>();
            deposit_wrapper.eq("accountid",account_id);
            DemandDeposit deposit = depositMapper.selectOne(deposit_wrapper);
            //获得系统时间
            Long date=System.currentTimeMillis();
            //上次存款变动时间
            Long old_date=deposit.getdate();
            Long length=date-old_date;
            //时间差天数的余数
            Long yu=length%3600000L;
            //时间差天数
            length=length/3600000L;
            Integer day=length.intValue();
            if(yu.compareTo(0L)==1){
                day=day+1;
            }
            //获得增加的基数
            BigDecimal b_day= new BigDecimal(Integer.toString(day));
            BigDecimal new_base=old_amount.multiply(b_day);
            BigDecimal base=deposit.getbase();
            base=base.add(old_amount);
            //更新基数
            UpdateWrapper<DemandDeposit> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("accountid", account_id);
            updateWrapper.set("base",base);
            updateWrapper.set("date",date);
            depositMapper.update(null,updateWrapper);
            return new ApiResult(true,deposit);
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
    private BigDecimal getrates(){
        //创建实例
        //QueryWrapper wrapper = new QueryWrapper<>();
        //List<Rate> rate = rateMapper.selectList(wrapper);
        //if(rate.size()==0) {
            //return new BigDecimal(Integer.toString(-1));
        //}else {
            //找到了就获得日利率
            //BigDecimal demand_rate = new BigDecimal(Integer.toString(0));
            //demand_rate=rate.get(0).getDemand_rate();
            //return demand_rate;
       // }
        BigDecimal rates=new BigDecimal("0.000005555555555");
        return rates;
    }
    public ApiResult settleinterrst(Long account_id){
        try{
            //创建实例
            QueryWrapper deposit_wrapper = new QueryWrapper<>();
            deposit_wrapper.eq("accountid",account_id);
            DemandDeposit deposit = depositMapper.selectOne(deposit_wrapper);
            BigDecimal amount = deposit.getamount();
            BigDecimal base = deposit.getbase();
            BigDecimal rate = getrates();
            if(rate.compareTo(BigDecimal.valueOf(0)) == -1){
                return new ApiResult(false,"日利率获得错误");
            }
            //利息=基数*利率
            BigDecimal interrst = base.multiply(rate);
            amount = amount.add(interrst);
            Long date=System.currentTimeMillis();
            //更新数据库
            UpdateWrapper<DemandDeposit> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", account_id);
            updateWrapper.set("amount",amount);
            updateWrapper.set("base",base);
            updateWrapper.set("date",date);
            depositMapper.update(null,updateWrapper);
            ApiResult addStatement1 = statementService.addStatement(account_id,interrst,System.currentTimeMillis(), StatementType.DemandInterest,account_id);
            return new ApiResult(true,deposit);
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult showamount(Long account_id){
        try{
            //创建实例行返回
            QueryWrapper deposit_wrapper = new QueryWrapper<>();
            deposit_wrapper.eq("accountid",account_id);
            DemandDeposit deposit = depositMapper.selectOne(deposit_wrapper);
            BigDecimal amount = deposit.getamount();
            return new ApiResult(true,amount);
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }

    }

    public ApiResult getDemandDepositByPropertyAccountId(Long account_id){
        try{
            //创建实例行返回
            QueryWrapper deposit_wrapper = new QueryWrapper<>();
            deposit_wrapper.eq("accountid",account_id);
            DemandDeposit deposit = depositMapper.selectOne(deposit_wrapper);
            return new ApiResult(true,deposit);
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

}
