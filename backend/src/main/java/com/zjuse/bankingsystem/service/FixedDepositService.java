package com.zjuse.bankingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjuse.bankingsystem.entity.FixedDeposit;
import com.zjuse.bankingsystem.mapper.FixedDepositMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.StatementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FixedDepositService {
    @Autowired
    private StatementService statementService;
    @Autowired
    FixedDepositMapper fixedDepositMapper;

    public ApiResult Save(Long propertyId, Long accountId, BigDecimal amount,int length,BigDecimal interestRate,boolean autocontinue) {
        try{
            FixedDeposit fixedDeposit = new FixedDeposit();
            fixedDeposit.setPropertyid(propertyId);
            fixedDeposit.setAccountid(accountId);
            fixedDeposit.setAmount(amount);
            fixedDeposit.setInterestRate(interestRate);
            fixedDeposit.setAutocontinue(autocontinue);
            fixedDeposit.setLength(length);
            fixedDeposit.setDate(System.currentTimeMillis());
            fixedDepositMapper.insert(fixedDeposit);
            return new ApiResult(true,fixedDeposit);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult getFixedDepositByPropertyId(Long propertyId) {
        try{
            QueryWrapper<FixedDeposit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("propertyid", propertyId);
            List<FixedDeposit> fixedDeposits= fixedDepositMapper.selectList(queryWrapper);
            if(fixedDeposits.size()==0){
                return new ApiResult(false,"资产不存在");
            }
            return new ApiResult(true,fixedDeposits.get(0));
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult getFixedDepositByAccountId(Long accountId) {
        try{
            QueryWrapper<FixedDeposit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("accountid", accountId);
            List<FixedDeposit> fixedDeposits= fixedDepositMapper.selectList(queryWrapper);
            if(fixedDeposits.size()==0){
                return  new ApiResult(false,"无定期资产");
            }
            return new ApiResult(true,fixedDeposits);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult Draw(Long propertyId) {
        try{
            QueryWrapper<FixedDeposit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("propertyid", propertyId);
            List<FixedDeposit> fixedDeposits= fixedDepositMapper.selectList(queryWrapper);
            if(fixedDeposits.size()==0){
                return new ApiResult(false,"资产不存在");
            }
            FixedDeposit fixedDeposit = fixedDeposits.get(0);
            Integer length = fixedDeposit.getLength();
            Long date  = fixedDeposit.getDate() + length * 2592000000L;
            Long currentTime = System.currentTimeMillis();
            BigDecimal interestRate = fixedDeposit.getInterestRate();
            BigDecimal rates=new BigDecimal("0.000005555555555");
            BigDecimal amount = fixedDeposit.getAmount();
            BigDecimal time = new BigDecimal(Integer.toString(length));
            BigDecimal yue = new BigDecimal(Integer.toString(1200));
            time=time.divide(yue);
            if(currentTime >= date){
                BigDecimal interest = interestRate.multiply(amount).multiply(time);
                fixedDeposit.setAmount(amount.add(interest));
            }else{
                BigDecimal interest = rates.multiply(amount).multiply(time);
                fixedDeposit.setAmount(amount.add(interest));
            }
            fixedDepositMapper.delete(queryWrapper);
            return new ApiResult(true,fixedDeposit);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult autoContinue() {
        try{
            QueryWrapper<FixedDeposit> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("autocontinue", true);
            List<FixedDeposit> fixedDeposits= fixedDepositMapper.selectList(queryWrapper);
            if(fixedDeposits.size()==0){
                return new ApiResult(false,"无需续存");
            }
            int count =0;
            for(FixedDeposit fixedDeposit : fixedDeposits){
                Long id = fixedDeposit.getPropertyid();
                Integer length = fixedDeposit.getLength();
                Long date  = fixedDeposit.getDate() + length * 2592000000L;
                Long currentTime = System.currentTimeMillis();
                BigDecimal interestRate = fixedDeposit.getInterestRate();
                BigDecimal amount = fixedDeposit.getAmount();
                BigDecimal time = new BigDecimal(Integer.toString(length));
                BigDecimal yue = new BigDecimal(Integer.toString(1200));
                time=time.divide(yue);
                if(currentTime >= date){
                    count++;
                    BigDecimal interest = interestRate.multiply(amount).multiply(time);
                    BigDecimal newamount = amount.add(interest);
                    UpdateWrapper<FixedDeposit> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("propertyid", id);
                    updateWrapper.set("amount", newamount);
                    updateWrapper.set("date", currentTime);
                    fixedDepositMapper.update(updateWrapper);
                    ApiResult addStatement1 = statementService.addStatement(id,interest,System.currentTimeMillis(), StatementType.FixedInterest,id);
                }
            }

            return new ApiResult(true,count);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
}
