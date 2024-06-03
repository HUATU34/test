package com.zjuse.bankingsystem;

import com.zjuse.bankingsystem.entity.DemandDeposit;
import com.zjuse.bankingsystem.entity.Property;
import com.zjuse.bankingsystem.entity.Rate;
import com.zjuse.bankingsystem.mapper.RateMapper;
import com.zjuse.bankingsystem.service.DemandDepositService;
import com.zjuse.bankingsystem.service.PropertyService;
import com.zjuse.bankingsystem.service.RateService;
import com.zjuse.bankingsystem.utils.ApiResult;
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
public class PropertyServiceTest {
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private DemandDepositService demandDepositService;
    @Autowired
    private RateService rateService;
    @Autowired
    private RateMapper rateMapper;

    private  int n = 100;

    @Test
    public void addPropertyTest() {
        List<Property> properties = new ArrayList<>();
        ApiResult apiResult = propertyService.addProperty(000000000001L,PropertyType.demand);
        properties.add((Property) apiResult.payload);
        for(int i = 0; i < n; i++){
            ApiResult apiResult1 = propertyService.addProperty(000000000001L,PropertyType.fixed);
            properties.add((Property) apiResult1.payload);
        }
        List<Property> propertyList = (List<Property>) propertyService.getPropertyByAccountId(000000000001L).payload;
        assertEquals(n+1,propertyList.size());
        for(Property property : properties){
            Property property1 = (Property) propertyService.getPropertyByPropertyId(property.getId()).payload;
            assertEquals(property, property1);
        }
        ApiResult apiResult1 = propertyService.addProperty(000000000001L,PropertyType.demand);
        assertFalse(apiResult1.ok);
    }

    @Test
    public void deletePropertyTest() {
        for(int i = 0; i < n; i++){
            ApiResult apiResult1 = propertyService.addProperty(000000000001L,PropertyType.fixed);
        }
        List<Property> properties = (List<Property>) propertyService.getPropertyByAccountId(000000000001L).payload;
        for(Property property : properties){
            propertyService.deleteProperty(property.getId());
            ApiResult apiResult1 = propertyService.getPropertyByPropertyId(property.getId());
            assertFalse(apiResult1.ok);
        }
    }

}
