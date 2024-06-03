package com.zjuse.bankingsystem.controller;

import com.zjuse.bankingsystem.service.CashierService;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter/cashier")
public class CashierController {
    @Autowired
    private CashierService cashierService;

    @PostMapping("/login")
    public RespResult loginCashier(@RequestParam("id") Long id, @RequestParam("password") String password) {
        System.out.println("loginCashier where id = " + id + " and password = " + password);
        ApiResult apiResult = cashierService.verifyCashier(id, password);
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return RespResult.fail(apiResult.message);
    }

    @PostMapping("/modify")
    public RespResult modifyPassword(@RequestParam("id") Long id, @RequestParam("oldpassword") String oldpassword, @RequestParam("newpassword") String newpassword) {
        System.out.println("modifyCashier where id = " + id + " and oldpassword = " + oldpassword + " and newpassword = " + newpassword);
        ApiResult apiResult1 = cashierService.verifyCashier(id, oldpassword);
        if(!apiResult1.ok)
            return RespResult.fail(apiResult1.message);
        ApiResult apiResult = cashierService.changePassword(id, newpassword);
        if(!apiResult.ok)
            return RespResult.fail(apiResult.message);
        else
            return RespResult.success(apiResult.payload);
    }
}
