package com.zjuse.bankingsystem.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjuse.bankingsystem.entity.Administrator;
import com.zjuse.bankingsystem.mapper.AdminMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public ApiResult getAllAdmin(){
        try{
            List<Administrator> admins = adminMapper.selectList(null);
            return new ApiResult(true,admins);
        } catch (Exception e){
            return  new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult getAdminById(Long id){
        try{
            QueryWrapper<Administrator> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            List<Administrator> admins = adminMapper.selectList(queryWrapper);
            if(admins.size()==0){
                return new ApiResult(false,"账号不存在");
            }
            return new ApiResult(true,admins.get(0));
        }catch (Exception e){
            return  new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult addAdmin(String adminName, String password) {
        try{
            //加密密码
            Random rand = new Random(2*System.currentTimeMillis());
            String salt= String.valueOf(rand.nextInt(900001)+100000);
            String newpassword = DigestUtil.sha256Hex(password+salt);
            Administrator admin = new Administrator();
            admin.setSalt(salt);
            admin.setPassword(newpassword);
            admin.setUsername(adminName);
            adminMapper.insert(admin);
            return new ApiResult(true,admin);
        }catch (Exception e){
            return new ApiResult(false, e.getMessage());
        }
    }

    public ApiResult verifyAdmin(Long id, String password) {
        try{
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            List<Administrator> admin = adminMapper.selectList(queryWrapper);
            if(admin.size()==0){
                return new ApiResult(false,"账号不存在");
            }
            String newpassword = DigestUtil.sha256Hex(password+admin.get(0).getSalt());
            if(newpassword.equals(admin.get(0).getPassword())){
                return new ApiResult(true,admin.get(0));
            }else{
                return new ApiResult(false,"密码错误");
            }
        }catch (Exception e){
            return new ApiResult(false, e.getMessage());
        }
    }

    public ApiResult deleteAdmin(Long id) {
        try{
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            List<Administrator> admin = adminMapper.selectList(queryWrapper);
            if(admin.size()==0){
                return new ApiResult(false,"账号不存在");
            }
            QueryWrapper queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", id);
            adminMapper.delete(queryWrapper1);
            return new ApiResult(true,"删除成功");
        }catch (Exception e){
            return new ApiResult(false, e.getMessage());
        }
    }

    public ApiResult ChangePassword(Long id, String newPassword) {
        try{
            QueryWrapper queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            List<Administrator> admin = adminMapper.selectList(queryWrapper);
            if(admin.size()==0){
                return new ApiResult(false,"账号不存在");
            }
            String newpassword = DigestUtil.sha256Hex(newPassword+admin.get(0).getSalt());
            UpdateWrapper<Administrator> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("password", newpassword);
            adminMapper.update(null, updateWrapper);
            return new ApiResult(true,"修改成功");
        }catch (Exception e){
            return new ApiResult(false, e.getMessage());
        }
    }
}
