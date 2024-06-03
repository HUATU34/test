package com.zjuse.bankingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjuse.bankingsystem.entity.Statement;
import com.zjuse.bankingsystem.mapper.StatementMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.StatementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatementService {
    @Autowired
    private StatementMapper statementMapper;

    public ApiResult getAllStatements() {
        try{
            QueryWrapper queryWrapper = new QueryWrapper();
            List<Statement> statements = statementMapper.selectList(queryWrapper);
            if(statements.size()==0){
                return new ApiResult(false,"无流水");
            }
            return new ApiResult(true,statements);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult getStatementById(Long id) {
        try{
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("accountid", id);
            List<Statement> statements = statementMapper.selectList(queryWrapper);
            if(statements.size()==0){
                return new ApiResult(false,"无流水");
            }
            return new ApiResult(true,statements);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult addStatement(Long accountId, BigDecimal amount, Long date, StatementType type, Long traced) {
        try{
            Statement statement = new Statement();
            statement.setaccountid(accountId);
            statement.setamount(amount);
            statement.setdate(date);
            statement.setType(type);
            statement.settraced(traced);
            statementMapper.insert(statement);
            return  new ApiResult(true,statement);
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
}
