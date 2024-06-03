package com.zjuse.bankingsystem;

import com.zjuse.bankingsystem.entity.DemandDeposit;
import com.zjuse.bankingsystem.entity.FixedDeposit;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.service.DemandDepositService;
import com.zjuse.bankingsystem.service.PropertyService;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.PropertyType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MapperScan("com.zjuse.bankingsystem.mapper")
public class DemandDepositTest {
    @Autowired
    private DemandDepositService depositService;

    @Autowired
    private PropertyService propertyService;
    private  int n =1000;
    @Test
    public void creatDemandDepositTest() {
        List<DemandDeposit> deposits = new ArrayList<>();
        Long propertyid = ((Property)propertyService.addProperty(000000000001L, PropertyType.demand).payload).getId();
        DemandDeposit deposit = (DemandDeposit) depositService.newDemandDeposit(000000000001L,propertyid).payload;
        deposits.add(deposit);
        DemandDeposit t_deposit=(DemandDeposit) depositService.getDemandDepositByPropertyAccountId(deposit.getpropertyid()).payload;
        //assertEquals(deposit,t_deposit);
    }
    @Test
    public void addDemandDepositTest() {
        List<DemandDeposit> deposits = new ArrayList<>();
        Long propertyid = ((Property)propertyService.addProperty(000000000001L, PropertyType.demand).payload).getId();
        DemandDeposit deposit = (DemandDeposit) depositService.newDemandDeposit(000000000001L,propertyid).payload;
        deposits.add(deposit);
        BigDecimal amount = new BigDecimal(Integer.toString(n));
        System.out.println(amount);
        ApiResult t_deposit=depositService.changeamount(000000000001L,amount);
        assertTrue(t_deposit.ok);
        t_deposit=depositService.showamount(000000000001L);
        System.out.println(t_deposit.payload);
        assertTrue(t_deposit.ok);
        amount = new BigDecimal(Integer.toString(n/2));
        t_deposit=depositService.changeamount(000000000001L,amount.negate());
        assertTrue(t_deposit.ok);
        t_deposit=depositService.showamount(000000000001L);
        System.out.println(t_deposit.payload);
        assertTrue(t_deposit.ok);
    }
}
