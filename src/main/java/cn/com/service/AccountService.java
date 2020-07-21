package cn.com.service;

import cn.com.bean.Account;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountService{

    Account findById(Integer id);

    boolean insertAccount(Account account);

    boolean updateAccount(Account account);

    boolean deleteByIds(@Param("ids") Integer...ids);

    List<Account> findAll(Account account);

    List<Account> selectByExample(Account account);

}
