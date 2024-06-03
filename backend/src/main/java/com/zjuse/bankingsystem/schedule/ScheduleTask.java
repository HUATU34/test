package com.zjuse.bankingsystem.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.mapper.AccountMapper;
import com.zjuse.bankingsystem.service.AccountService;
import com.zjuse.bankingsystem.service.DemandDepositService;
import com.zjuse.bankingsystem.service.FixedDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleTask
{
    @Autowired
    private FixedDepositService fixedDepositService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private DemandDepositService demandDepositService;

    @Scheduled(cron="0 0 12 * * ?")
    public void autoContinue(){
       fixedDepositService.autoContinue();
    }

    @Scheduled(cron = "0 0 0 20 * ?")
    public void settleBase(){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        for (Account account : accounts) {
            demandDepositService.settleinterrst(account.getId());
        }
    }
}