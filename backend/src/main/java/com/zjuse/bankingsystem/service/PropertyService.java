    package com.zjuse.bankingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.mapper.PropertyMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    PropertyMapper propertyMapper;

    public ApiResult getPropertyByPropertyId(Long id) {
        try{
            QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            List<Property> propertyList = propertyMapper.selectList(queryWrapper);
            if(propertyList.size()==0){
                return new ApiResult(false,"资产不存在");
            }
            return new ApiResult(true,propertyList.get(0));
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult getPropertyByAccountId(Long accountId) {
        try{
            QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("accountid", accountId);
            List<Property> propertyList = propertyMapper.selectList(queryWrapper);
            if(propertyList.size()==0){
                return new ApiResult(false,"无资产");
            }
            return new ApiResult(true,propertyList);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult addProperty(Long accountId, PropertyType propertyType) {
        try{
            if(propertyType==PropertyType.demand){
                QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("accountid", accountId).eq("type", PropertyType.demand);
                List<Property> propertyList = propertyMapper.selectList(queryWrapper);
                if(propertyList.size()!=0){
                    return new ApiResult(false,"只能有一笔定期存款");
            }
            }
            Property property = new Property();
            property.setAccountid(accountId);
            property.setType(propertyType);
            propertyMapper.insert(property);
            return new ApiResult(true,property);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult deleteProperty(Long propertyId) {
        try{
            QueryWrapper<Property> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", propertyId);
            propertyMapper.delete(queryWrapper);
            return new ApiResult(true,"删除成功");
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
}
