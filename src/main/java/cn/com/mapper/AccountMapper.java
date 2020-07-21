package cn.com.mapper;

import cn.com.bean.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {

    Account findById(Integer id);

    int insertAccount(Account account);

    int updateAccount(Account account);

    int deleteByIds(@Param("ids") Integer...ids);

    List<Account> findAll(Account account);

    List<Account> selectByExample(Account account);
}