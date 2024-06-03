package com.zjuse.bankingsystem.controller;

import com.zjuse.bankingsystem.service.AdminService;
import com.zjuse.bankingsystem.service.CashierService;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private CashierService cashierService;

    @PostMapping("/counter/admin/login/log")
    public RespResult loginAdmin(@RequestParam("id") Long id, @RequestParam("password") String password) {
        System.out.println("loginAdmin where id = '" + id + "' and password = '" + password + "'");
        ApiResult apiResult  = adminService.verifyAdmin(id, password);
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return RespResult.fail(apiResult.message);
    }

    @GetMapping("/counter/admin/cashier")
    public RespResult queryCashier() {
        ApiResult apiResult  = cashierService.getAllCashier();
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return RespResult.fail(apiResult.message);
    }

    @PostMapping("/counter/admin/modify")
    public RespResult modifyPassword(@RequestParam("id") Long id, @RequestParam("password") String newpassword, @RequestParam("oldpassword")String oldpassword) {
        System.out.println("modifyPassword where id = '" + id + "' and password = '" + newpassword + "' and oldpassword = '" + oldpassword + "'");
        ApiResult apiResult1 = adminService.verifyAdmin(id, oldpassword);
        if(!apiResult1.ok)
            return RespResult.fail(apiResult1.message);
        ApiResult apiResult = adminService.ChangePassword(id, newpassword);
        if(apiResult.ok)
            return RespResult.success(apiResult.message);
        else
            return RespResult.fail(apiResult.message);
    }

    @DeleteMapping("/counter/admin/cashier/delete")
    public RespResult deleteCashier(@RequestParam("id") Long id) {
        System.out.println("deleteCashier where id = '" + id + "'");
        ApiResult apiResult = cashierService.deleteCashierById(id);
        if(apiResult.ok)
            return RespResult.success(apiResult.message);
        else
            return RespResult.fail(apiResult.message);
    }

    @PostMapping("/counter/admin/cashier/add")
    public RespResult addCashier(@RequestParam("password") String password, @RequestParam("username")String username,@RequestParam("authority")int authority) {
        System.out.println("addCashier where password = '" + password + "' and username = '" + username + "'");
        ApiResult apiResult = cashierService.addCashier(username,password,authority);
        if(apiResult.ok)
            return RespResult.success(apiResult.payload);
        else
            return RespResult.fail(apiResult.message);
    }

    @PostMapping("/counter/admin/cashier/authority")
    public RespResult addAuthority(@RequestParam("id") Long id, @RequestParam("authority")int authority) {
        System.out.println("addAuthority where id = '" + id + "' and authority = '" + authority + "'");
        ApiResult apiResult = cashierService.changeAuthority(id, authority);
        if(apiResult.ok)
            return RespResult.success(apiResult.message);
        else
            return RespResult.fail(apiResult.message);
    }
}
