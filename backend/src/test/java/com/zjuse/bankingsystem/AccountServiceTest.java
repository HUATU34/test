package com.zjuse.bankingsystem;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.entity.FixedDeposit;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.mapper.AccountMapper;
import com.zjuse.bankingsystem.service.*;
import com.zjuse.bankingsystem.utils.AccountStatus;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.CardType;
import com.zjuse.bankingsystem.utils.PropertyType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@MapperScan("com.zjuse.bankingsystem.mapper")
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private DemandDepositService demandDepositService;

    @Autowired
    private FixedDepositService fixedDepositService;

    private int n = 1;

    @Test
    public void addAndVerifyAccountTest() {
        List<Account> accounts = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Account account =(Account) accountService.newAccount("account"+i,"123456", AccountStatus.Normal,"account"+i, CardType.Card,"2011111").payload;
            accounts.add(account);
        }
        for(int i = 0; i < n; i++){
            ApiResult verify = accountService.VerifyPassword(accounts.get(i).getCardId(),"account"+i);
            assertTrue(verify.ok);
        }
        for(int i = 0; i < n; i++){
            accountService.ChangePassword("account1"+i,accounts.get(i).getId());
            accountService.ChangeStatus(AccountStatus.Frozen,accounts.get(i).getId());
        }
        for(int i = 0; i < n; i++){
            ApiResult verify = accountService.VerifyPassword(accounts.get(i).getCardId(),"account"+i);
            assertFalse(verify.ok);
            ApiResult verify2 = accountService.VerifyPassword(accounts.get(i).getCardId(),"account1"+i);
            assertTrue(verify2.ok);
            AccountStatus status = ((Account)accountService.getAccountByAccountId(accounts.get(i).getId()).payload).getStatus();
            assertEquals(AccountStatus.Frozen,status);
        }
    }

    @Test
    public void deleteAccountTest() {
        List<Account> accounts = new ArrayList<>();
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        accounts = accountMapper.selectList(queryWrapper);
        for(int i = 1; i < accounts.size(); i++){
            accountService.DeleteAccountByAccountId(accounts.get(i).getId());
            ApiResult search = accountService.getAccountByAccountId(accounts.get(i).getId());
            assertFalse(search.ok);
        }
        Account account1 =(Account) accountService.newAccount("account","123456", AccountStatus.Normal,"account", CardType.Card,"2011111").payload;
        Account account2 = (Account) accountService.newAccount("account","123456", AccountStatus.Normal,"account", CardType.Card,"2011111").payload;
        demandDepositService.changeamount(account1.getId(),new BigDecimal("100"));
        Long propertyid = ((Property)propertyService.addProperty(account2.getId(), PropertyType.fixed).payload).getId();
        FixedDeposit fixedDeposit = (FixedDeposit) fixedDepositService.Save(propertyid,account2.getId(),new BigDecimal("10.01"),12,new BigDecimal("10.02"),true).payload;
        assertFalse(accountService.DeleteAccountByAccountId(account1.getId()).ok);
        assertFalse(accountService.DeleteAccountByAccountId(account2.getId()).ok);
    }
}
