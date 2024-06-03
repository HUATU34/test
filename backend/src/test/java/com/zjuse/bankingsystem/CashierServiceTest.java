package com.zjuse.bankingsystem;

import com.zjuse.bankingsystem.entity.Administrator;
import com.zjuse.bankingsystem.entity.Cashier;
import com.zjuse.bankingsystem.service.CashierService;
import com.zjuse.bankingsystem.utils.ApiResult;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@MapperScan("com.zjuse.bankingsystem.mapper")
public class CashierServiceTest {
    @Autowired
    private CashierService cashierService;

    private  int n = 100;

    @Test
    public void addAndvVerifyCashier() {
        List<Cashier> cashiers = new ArrayList<>();
        for (int i=0;i<n;i++){
            Cashier cashier = (Cashier) cashierService.addCashier("cashier"+i,"cashier"+i,1).payload;
            cashiers.add(cashier);
        }
        for(int i=0;i<n;i++){
            ApiResult verify = cashierService.verifyCashier(cashiers.get(i).getId(), "cashier"+i);
            assertTrue(verify.ok);
        }
        for(int i=0;i<n;i++){
            ApiResult change = cashierService.changePassword(cashiers.get(i).getId(),"cashier1"+i);
        }
        for(int i=0;i<n;i++){
            ApiResult verify1 = cashierService.verifyCashier(cashiers.get(i).getId(), "cashier1"+i);
            assertTrue(verify1.ok);
            ApiResult verify2 = cashierService.verifyCashier(cashiers.get(i).getId(), "cashier"+i);
            assertFalse(verify2.ok);
        }
    }

    @Test
    public void deleteCashier() {
        List<Cashier> cashiers = (List<Cashier>)cashierService.getAllCashier().payload;
        for (int i=0;i<cashiers.size();i++){
            cashierService.deleteCashierById(cashiers.get(i).getId());
            ApiResult search = cashierService.getCashierById(cashiers.get(i).getId());
            assertFalse(search.ok);
        }
    }
}
