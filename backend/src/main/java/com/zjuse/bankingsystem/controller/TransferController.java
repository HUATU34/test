package com.zjuse.bankingsystem.controller;


import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.service.AccountService;
import com.zjuse.bankingsystem.service.CashierService;
import com.zjuse.bankingsystem.service.DemandDepositService;
import com.zjuse.bankingsystem.service.StatementService;
import com.zjuse.bankingsystem.utils.AccountStatus;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.RespResult;
import com.zjuse.bankingsystem.utils.StatementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    @Autowired
    private DemandDepositService depositService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StatementService statementService;
    @Autowired
    private CashierService cashierService;

    @PostMapping("/counter/transfer")
    public RespResult transfer(@RequestParam("id") Long card_id,@RequestParam("password")String password,@RequestParam("toid") Long to_id, @RequestParam("amount")BigDecimal amount,@RequestParam("operatorid")Long operatorid) {
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
            return RespResult.fail("转账账户状态异常");

        //检验卡号，获得账号
        ApiResult account1Result  = accountService.getAccountByCardId(card_id);
        if(!account1Result.ok)
            return RespResult.fail(account1Result.message);
        Long toid=((Account)account1Result.payload).getId();
        //检验账号状态
        ApiResult statustoResult  = accountService.getAccountByCardId(to_id);
        if(!statustoResult.ok)
            return RespResult.fail(statustoResult.message);
        AccountStatus statesto=((Account)statustoResult.payload).getStatus();
        if(!statesto.equals(normal))
            return RespResult.fail("收账账户状态异常");


        ApiResult apiResult  = depositService.changeamount(id,amount.negate());
        if(!apiResult.ok)
            return RespResult.success(apiResult.payload);
        ApiResult toapiResult  = depositService.changeamount(toid,amount);
        if(!toapiResult.ok){
            apiResult  = depositService.changeamount(id,amount);
            return RespResult.fail(toapiResult.message);
        }
        ApiResult addStatement1 = statementService.addStatement(id,amount,System.currentTimeMillis(), StatementType.TransferOut,toid);
        ApiResult addStatement2 = statementService.addStatement(toid,amount,System.currentTimeMillis(), StatementType.TransferIn,id);
        return RespResult.success("转账成功");
    }
}
