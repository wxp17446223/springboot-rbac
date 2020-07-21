package cn.com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.com.mapper.AccountMapper;
import cn.com.bean.Account;
import cn.com.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    @Resource
    private AccountMapper accountMapper;

    @Override
    public Account findById(Integer id) {
        return accountMapper.findById(id);
    }

    @Override
    public boolean insertAccount(Account account) {
        return accountMapper.insertAccount(account)>0;
    }

    @Override
    public boolean updateAccount(Account account) {
        try{
            return accountMapper.updateAccount(account)>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByIds(Integer... ids) {
        return accountMapper.deleteByIds(ids)>0;
    }

    @Override
    public List<Account> findAll(Account account) {
        return accountMapper.findAll(account);
    }

    @Override
    public List<Account> selectByExample(Account account) {
        return accountMapper.selectByExample(account);
    }
}
