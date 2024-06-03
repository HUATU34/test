package com.zjuse.bankingsystem.controller;

import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.entity.DemandDeposit;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.service.*;
import com.zjuse.bankingsystem.utils.AccountStatus;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.RespResult;
import com.zjuse.bankingsystem.utils.StatementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/dp")
public class DemandDepositController {
    @Autowired
    private DemandDepositService depositService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private CashierService cashierService;
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/counter/dp/save")
    public RespResult saveDemandDeposit(@RequestParam("id") Long card_id, @RequestParam("amount")BigDecimal amount,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<1){
            return  RespResult.fail("权限不足");
        }
        //由卡号获得账户
        ApiResult accountResult  = accountService.getAccountByCardId(card_id);
        if(!accountResult.ok)
            return RespResult.fail(accountResult.message);
        Long id=((Account)accountResult.payload).getId();
        //检查账户状态
        AccountStatus states=((Account)accountResult.payload).getStatus();
        AccountStatus normal=AccountStatus.Normal;
        if(!states.equals(normal))
            return RespResult.fail("账户状态异常");

        ApiResult propertyResult=depositService.getDemandDepositByPropertyAccountId(id);
        //新建实例并返回
        ApiResult apiResult  = depositService.changeamount(id,amount);
        if(!apiResult.ok)
            return RespResult.fail(apiResult.message);
        ApiResult addStatement1 = statementService.addStatement(id,amount,System.currentTimeMillis(), StatementType.DemandDeposit,((DemandDeposit)propertyResult.payload).getpropertyid());
        return RespResult.success("存款成功");
    }

    @PostMapping("/counter/dp/draw")
    public RespResult drawDemandDeposit(@RequestParam("id") Long card_id,@RequestParam("password")String password, @RequestParam("amount")BigDecimal amount,@RequestParam("operatorid")Long operatorid) {
        //System.out.println("loginAdmin where id = '" + id + "' and password = '" + password + "'");
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
            
        ApiResult propertyResult=depositService.getDemandDepositByPropertyAccountId(id);
        //新建实例并返回
        ApiResult apiResult  = depositService.changeamount(id,amount.negate());
        if(!apiResult.ok)
            return RespResult.fail(apiResult.message);
        ApiResult addStatement1 = statementService.addStatement(id,amount,System.currentTimeMillis(), StatementType.DemandDraw,((DemandDeposit)propertyResult.payload).getpropertyid());
        return RespResult.success("取款成功");
    }

    @GetMapping("/counter/dp/show")
    public RespResult showDemandDeposit(@RequestParam("id") Long card_id,@RequestParam("password")String password,@RequestParam("operatorid")Long operatorid) {
        //System.out.println("loginAdmin where id = '" + id + "' and password = '" + password + "'");
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
        ApiResult apiResult  = depositService.showamount(id);
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return RespResult.fail(apiResult.message);
    }
}
