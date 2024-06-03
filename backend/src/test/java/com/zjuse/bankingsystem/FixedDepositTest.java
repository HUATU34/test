package com.zjuse.bankingsystem;

import com.zjuse.bankingsystem.entity.FixedDeposit;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.service.FixedDepositService;
import com.zjuse.bankingsystem.service.PropertyService;
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
public class FixedDepositTest {
    @Autowired
    private FixedDepositService fixedDepositService;

    @Autowired
    private PropertyService propertyService;

    private  int n =1000;

    @Test
    public void createFixedDepositTest() {
        List<FixedDeposit> fixedDeposits = new ArrayList<>();
        for(int i=0;i<n;i++){
            Long propertyid = ((Property)propertyService.addProperty(000000000001L, PropertyType.fixed).payload).getId();
            FixedDeposit fixedDeposit = (FixedDeposit) fixedDepositService.Save(propertyid,000000000001L,new BigDecimal(Integer.toString(i+10)+".01"),12,new BigDecimal("10.00"),true).payload;
            fixedDeposits.add(fixedDeposit);
        }
        for(FixedDeposit fixedDeposit:fixedDeposits){
            assertEquals(fixedDeposit,(FixedDeposit) fixedDepositService.getFixedDepositByPropertyId(fixedDeposit.getPropertyid()).payload);
        }
    }

    @Test
    public void drawFixedDepositTest() {
        List<FixedDeposit> fixedDeposits = new ArrayList<>();
        for(int i=0;i<n;i++){
            Long propertyid = ((Property)propertyService.addProperty(000000000001L, PropertyType.fixed).payload).getId();
            FixedDeposit fixedDeposit = (FixedDeposit) fixedDepositService.Save(propertyid,000000000001L,new BigDecimal(Integer.toString(i+10)+".01"),12,new BigDecimal("10.02"),true).payload;
            fixedDeposits.add(fixedDeposit);
        }
        for(FixedDeposit fixedDeposit:fixedDeposits){
            assertTrue(fixedDepositService.getFixedDepositByPropertyId(fixedDeposit.getPropertyid()).ok);
            fixedDepositService.Draw(fixedDeposit.getPropertyid());
            assertFalse(fixedDepositService.getFixedDepositByPropertyId(fixedDeposit.getPropertyid()).ok);
        }
    }

    @Test
    public void autoContinueTest(){
        List<FixedDeposit> fixedDeposits = new ArrayList<>();
        for(int i=0;i<n;i++){
            Long propertyid = ((Property)propertyService.addProperty(000000000001L, PropertyType.fixed).payload).getId();
            FixedDeposit fixedDeposit = (FixedDeposit) fixedDepositService.Save(propertyid,000000000001L,new BigDecimal(Integer.toString(i+10)+".01"),0,new BigDecimal("10.00"),true).payload;
            fixedDeposits.add(fixedDeposit);
            FixedDeposit fixedDeposit1 = (FixedDeposit) fixedDepositService.Save(propertyid,000000000001L,new BigDecimal(Integer.toString(i+10)+".01"),0,new BigDecimal("10.00"),false).payload;
            fixedDeposits.add(fixedDeposit1);
        }
        assertTrue(fixedDepositService.autoContinue().ok);
    }
}
