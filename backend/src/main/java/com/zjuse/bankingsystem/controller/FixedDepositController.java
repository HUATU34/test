package com.zjuse.bankingsystem.controller;

import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.entity.FixedDeposit;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.entity.Rate;
import com.zjuse.bankingsystem.service.*;
import com.zjuse.bankingsystem.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/fp")
public class FixedDepositController {
    @Autowired
    private FixedDepositService depositService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private RateService rateService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private CashierService cashierService;

    @PostMapping("/counter/fp/save")
    public RespResult saveFixedDeposit(@RequestParam("id") Long card_id, @RequestParam("amount") BigDecimal amount, @RequestParam("Length") int length, @RequestParam("ifauto") boolean autocontinue,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<1){
            return  RespResult.fail("权限不足");
        }
        //由卡号获得账户
        ApiResult accountResult = accountService.getAccountByCardId(card_id);
        if (!accountResult.ok)
            return RespResult.fail(accountResult.message);
        Long id = ((Account) accountResult.payload).getId();
        //检查账户状态
        AccountStatus states = ((Account) accountResult.payload).getStatus();
        AccountStatus normal = AccountStatus.Normal;
        if (!states.equals(normal))
            return RespResult.fail("账户状态异常");
        //先向资产中插入新定期存款获得id
        PropertyType propertyType = PropertyType.fixed;
        ApiResult propertyResult = propertyService.addProperty(id, propertyType);
        if (!propertyResult.ok)
            return RespResult.fail(propertyResult.message);
        Long pid = ((Property) propertyResult.payload).getId();
        //获得利率
        BigDecimal rate = new BigDecimal(0L);
        //判断存款时长并获得利率
        switch (length) {
            case 3:
                rate = new BigDecimal("1.15");
            case 6:
                rate = new BigDecimal("1.35");
            case 12:
                rate = new BigDecimal("1.45");
            case 24:
                rate = new BigDecimal("1.65");
            case 36:
                rate = new BigDecimal("1.95");
            case 60:
                rate = new BigDecimal("2.00");
        }
        //新建实例并返回
        ApiResult apiResult = depositService.Save(pid, id, amount, length, rate, autocontinue);
        if (!apiResult.ok){
            propertyResult = propertyService.deleteProperty(pid);
            if (!propertyResult.ok) {
                return RespResult.fail(propertyResult.message);
            }
            return RespResult.fail(apiResult.message);
        }
        ApiResult addStatement1 = statementService.addStatement(id,amount,System.currentTimeMillis(), StatementType.FixedDeposit,((FixedDeposit)apiResult.payload).getPropertyid());
        return RespResult.success("定期存款成功");
    }

    @PostMapping("/counter/fp/draw")
    public RespResult drawFixedDeposit(@RequestParam("id") Long card_id,@RequestParam("password")String password, @RequestParam("propertyid")Long pid,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<1){
            return  RespResult.fail("权限不足");
        }
        //检验卡号密码，获得账号
        ApiResult checkResult  =accountService.VerifyPassword(card_id,password);
        if(!checkResult.ok)
            return RespResult.fail(checkResult.message);
        Long id=(Long)checkResult.payload;
        //检验账号状态
        ApiResult statusResult  = accountService.getAccountByCardId(card_id);
        if(!statusResult.ok)
            return RespResult.fail(statusResult.message);
        AccountStatus states=((Account)statusResult.payload).getStatus();
        AccountStatus normal=AccountStatus.Normal;
        if(!states.equals(normal))
            return RespResult.fail("账户状态异常");

        PropertyType propertyType = PropertyType.fixed;
        ApiResult apiResult  = depositService.Draw(pid);
        ApiResult propertyResult = propertyService.deleteProperty(pid);
        if (!propertyResult.ok)
            return RespResult.fail(propertyResult.message);
        //新建实例并返回
        BigDecimal draw_amount=((FixedDeposit)apiResult.payload).getAmount();
        if(!apiResult.ok)
            return RespResult.fail(apiResult.message);
        ApiResult addStatement1 = statementService.addStatement(id, ((FixedDeposit)apiResult.payload).getAmount(),System.currentTimeMillis(), StatementType.FixedDraw,pid);
        return RespResult.success(draw_amount);
    }

    @PostMapping("/counter/fp/list")
    public RespResult listFixedDeposit(@RequestParam("id") Long card_id,@RequestParam("password")String password,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<1){
            return  RespResult.fail("权限不足");
        }
        //检验卡号密码，获得账号
        ApiResult checkResult  =accountService.VerifyPassword(card_id,password);
        if(!checkResult.ok)
            return RespResult.fail(checkResult.message);
        Long id=(Long)checkResult.payload;
        //检验账号状态
        ApiResult statusResult  = accountService.getAccountByCardId(card_id);
        if(!statusResult.ok)
            return RespResult.fail(statusResult.message);
        AccountStatus states=((Account)statusResult.payload).getStatus();
        AccountStatus normal=AccountStatus.Normal;
        if(!states.equals(normal))
            return RespResult.fail("账户状态异常");

        //新建实例并返回
        ApiResult apiResult  = depositService.getFixedDepositByAccountId(id);
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return RespResult.fail(apiResult.message);
    }
}
