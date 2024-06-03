package com.zjuse.bankingsystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zjuse.bankingsystem.entity.Account;
import com.zjuse.bankingsystem.entity.Card;
import com.zjuse.bankingsystem.mapper.AccountMapper;
import com.zjuse.bankingsystem.mapper.PropertyMapper;
import com.zjuse.bankingsystem.mapper.CardMapper;
import com.zjuse.bankingsystem.utils.ApiResult;
import com.zjuse.bankingsystem.utils.CardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//负责
@Service
public class CardService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CardMapper cardMapper;
    @Autowired
    PropertyMapper propertyMapper;
    public ApiResult newCard(CardType cardType, Long accountid){
        try{
          //创建卡片实例
          Card card = new Card();
          card.setType(cardType);
          card.setAccountid(accountid);
           //插入卡片并获取id
           cardMapper.insert(card);
            return new ApiResult(true,card);
        }catch (Exception e){
            return  new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult bindCard(Long card_id,Long account_id){
        try {
            //搜素给定银行卡、绑定的账号和银行卡，创建实例
            Account account = accountMapper.selectById(account_id);
            //更新账户绑定的银行卡
            UpdateWrapper<Account> updateWrapper_account = new UpdateWrapper<>();
            updateWrapper_account.eq("id", account_id);
            updateWrapper_account.set("card_id",card_id);
            accountMapper.update(null,updateWrapper_account);
            return new ApiResult(true,card_id);
        }catch (Exception e){
            return  new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult DeleteCardByCardId(Long card_id){
        try{
            //获得card
            QueryWrapper<Card> queryWrapper_card = new QueryWrapper<>();
            queryWrapper_card.eq("id", card_id);
            Card card = cardMapper.selectOne(queryWrapper_card);
            Long account_id= card.getAccountid();
            //删除
            QueryWrapper card_wrapper = new QueryWrapper<>();
            card_wrapper.eq("id",card_id);
            cardMapper.delete(card_wrapper);
            return new ApiResult(true,"删除成功");
        }catch(Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
    public ApiResult getCardByAccountId(Long account_id){
        try{
            //搜索
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("accountid",account_id);
            List<Card> card = cardMapper.selectList(wrapper);
            //没找到
            if(card.size()==0) {
                return new ApiResult(false, "账户不存在");
            }else
                return new ApiResult(true,card.get(0));
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }

    public ApiResult getCardByCardId(Long card_id){
        try {
            //搜索
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("id",card_id);
            List<Card> card = cardMapper.selectList(wrapper);
            if(card.size()==0) {
                return new ApiResult(false, "账户不存在");
            }else
                return new ApiResult(true,card.get(0));
        }catch (Exception e){
            return new ApiResult(false,e.getMessage());
        }
    }
}
