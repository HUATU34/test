package com.zjuse.bankingsystem;

import com.zjuse.bankingsystem.entity.Administrator;
import com.zjuse.bankingsystem.service.AdminService;
import com.zjuse.bankingsystem.utils.ApiResult;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@MapperScan("com.zjuse.bankingsystem.mapper")
public class AdminServiceTest {
    @Autowired
    AdminService adminService;

    private int n = 100;

    @Test
    public void addAndvVerifyAdminTest() {
        List<Administrator> administrators = new ArrayList<>();
        for (int i=0;i<n;i++){
            Administrator admin = (Administrator) adminService.addAdmin("admin"+i,"admin"+i).payload;
            administrators.add(admin);
        }
        for(int i=0;i<n;i++){
            ApiResult verify = adminService.verifyAdmin(administrators.get(i).getId(), "admin"+i);
            assertTrue(verify.ok);
        }
        for(int i=0;i<n;i++){
            ApiResult change = adminService.ChangePassword(administrators.get(i).getId(),"admin1"+i);
        }
        for(int i=0;i<n;i++){
            ApiResult verify1 = adminService.verifyAdmin(administrators.get(i).getId(), "admin1"+i);
            assertTrue(verify1.ok);
            ApiResult verify2 = adminService.verifyAdmin(administrators.get(i).getId(), "admin"+i);
            assertFalse(verify2.ok);
        }
    }

    @Test
    public void deleteAdmin() {
        List<Administrator> admins = (List<Administrator>)adminService.getAllAdmin().payload;
        for (int i=0;i<admins.size();i++){
            adminService.deleteAdmin(admins.get(i).getId());
            ApiResult search = adminService.getAdminById(admins.get(i).getId());
            assertFalse(search.ok);
        }
    }
}
