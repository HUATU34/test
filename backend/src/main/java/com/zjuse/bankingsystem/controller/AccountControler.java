package com.zjuse.bankingsystem.controller;

import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.entity.Card;
import com.zjuse.bankingsystem.service.AccountService;
import com.zjuse.bankingsystem.service.CardService;
import com.zjuse.bankingsystem.service.CashierService;
import com.zjuse.bankingsystem.service.PropertyService;
import com.zjuse.bankingsystem.utils.AccountStatus;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.CardType;
import com.zjuse.bankingsystem.utils.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountControler {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CashierService cashierService;

    @PostMapping("/counter/account/add")//添加账户，输入姓名、电话号码、密码、卡片类型、身份证号，返回entity中的Account类
    public RespResult addAccount(@RequestParam("name")String name, @RequestParam("phoneNumber")String phoneNumber,@RequestParam("password")String password,@RequestParam("cardType") CardType cardType,@RequestParam("citizenid")String citizenid,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        AccountStatus accountStatus = AccountStatus.Normal;
        ApiResult apiResult = accountService.newAccount(name,phoneNumber,accountStatus,password,cardType,citizenid);
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return  RespResult.fail(apiResult.message);
    }

    @DeleteMapping("/counter/account/delete")//删除账户，输入卡号、密码删除账号，返回信息字符串
    public RespResult deleteAccount(@RequestParam("cardid")Long cardid, @RequestParam("password")String password,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 =accountService.VerifyPassword(cardid,password);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Long accountid = (Long)apiResult1.payload;
        ApiResult apiResult = accountService.DeleteAccountByAccountId(accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.message);
    }

    @PostMapping("/counter/account/modify/password")//更改密码，输入卡号、旧密码、新密码，返回信息字符串
    public RespResult modifyAccountPassword(@RequestParam("cardid")Long cardid,@RequestParam("oldpassword")String oldpassword,@RequestParam("newpassword")String newpassword,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.VerifyPassword(cardid,oldpassword);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Long accountid = (Long)apiResult1.payload;
        ApiResult apiResult = accountService.ChangePassword(newpassword,accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.message);
    }

    @PostMapping("/counter/account/modify/status/freeze")//冻结账户，输入卡号，返回信息字符串
    public RespResult freezeAccount(@RequestParam("cardid")Long cardid,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<3){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.getAccountByCardId(cardid);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Account account = (Account) apiResult1.payload;
        AccountStatus accountStatus = account.getStatus();
        if(accountStatus==AccountStatus.Frozen)
            return  RespResult.fail("帐户已被冻结");
        Long accountid = account.getId();
        AccountStatus accountStatus1 =AccountStatus.Frozen;
        ApiResult apiResult = accountService.ChangeStatus(accountStatus1,accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.message);
    }

    @PostMapping("/counter/account/modify/status/unfreeze")//解冻账户，输入卡号，返回信息字符串
    public RespResult unFreezeAccount(@RequestParam("cardid")Long cardid,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<3){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.getAccountByCardId(cardid);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Account account = (Account) apiResult1.payload;
        AccountStatus accountStatus = account.getStatus();
        if(accountStatus!=AccountStatus.Frozen)
            return  RespResult.fail("帐户未被冻结");
        Long accountid = account.getId();
        AccountStatus accountStatus1 =AccountStatus.Normal;
        ApiResult apiResult = accountService.ChangeStatus(accountStatus1,accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.message);
    }

    @PostMapping("/counter/account/modify/status/lost")
    public RespResult lostAccount(@RequestParam("cardid")Long cardid,@RequestParam("password")String password,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.VerifyPassword(cardid,password);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Long accountid = (Long)apiResult1.payload;
        ApiResult apiReult =accountService.getAccountByCardId(cardid);
        if(!apiReult.ok)
            return  RespResult.fail(apiReult.message);
        if(((Account)apiReult.payload).getStatus()!=AccountStatus.Normal)
            return  RespResult.fail("账户状态异常");
        ApiResult apiResult = accountService.ChangeStatus(AccountStatus.Lost,accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.message);
    }

    @PostMapping("/counter/account/modify/status/unlost")
    public RespResult unLostAccount(@RequestParam("cardid")Long cardid,@RequestParam String password,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.VerifyPassword(cardid,password);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Long accountid = (Long)apiResult1.payload;
        ApiResult apiReult =accountService.getAccountByCardId(cardid);
        if(!apiReult.ok)
            return  RespResult.fail(apiReult.message);
        if(((Account)apiReult.payload).getStatus()!=AccountStatus.Lost)
            return RespResult.fail("账户状态异常");
        ApiResult apiResult = accountService.ChangeStatus(AccountStatus.Normal,accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.message);
    }

    @PostMapping("/counter/account/modify/replace")
    public RespResult replaceAccount(@RequestParam("cardid")Long cardid,@RequestParam("password")String password,@RequestParam("cardtype")CardType cardType,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.VerifyPassword(cardid,password);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        Long accountid = (Long)apiResult1.payload;
        ApiResult apiReult =accountService.getAccountByCardId(cardid);
        if(!apiReult.ok)
            return  RespResult.fail(apiReult.message);
        if(((Account)apiReult.payload).getStatus()!=AccountStatus.Lost)
            return RespResult.fail("账户状态异常");
        ApiResult apiResult = cardService.newCard(cardType,accountid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        Card card = (Card) apiResult.payload;
        Long newcardid = card.getCardId();
        cardService.bindCard(newcardid,accountid);
        ApiResult deletecard = cardService.DeleteCardByCardId(cardid);
        accountService.ChangeStatus(AccountStatus.Normal,accountid);
        if(!deletecard.ok)
            return  RespResult.fail(deletecard.message);
        else
            return  RespResult.success(newcardid);
    }

    @GetMapping("/account/info")
    public RespResult getAccountInfo(@RequestParam("cardid")Long cardid, @RequestParam("password") String password,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<1){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.VerifyPassword(cardid,password);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        ApiResult apiResult = accountService.getAccountByCardId(cardid);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.payload);
    }

    @GetMapping("/account/property")
    public RespResult getAccountProperty(@RequestParam("cardid")Long cardid, @RequestParam("password") String password,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<1){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult1 = accountService.VerifyPassword(cardid,password);
        if(!apiResult1.ok)
            return  RespResult.fail(apiResult1.message);
        ApiResult apiResult = propertyService.getPropertyByAccountId((Long)apiResult1.payload);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.payload);
    }

    @GetMapping("/accounnt/list")
    public RespResult getAccountList(@RequestParam("citizenid")String citizenid,@RequestParam("name")String name,@RequestParam("operatorid")Long operatorid) {
        //验证权限
        ApiResult verify = cashierService.getAuthority(operatorid);
        if(!verify.ok||(int)verify.payload<2){
            return  RespResult.fail("权限不足");
        }
        ApiResult apiResult = accountService.getAccountByCitizenIdAndName(citizenid,name);
        if(!apiResult.ok)
            return  RespResult.fail(apiResult.message);
        else
            return  RespResult.success(apiResult.payload);
    }
}
