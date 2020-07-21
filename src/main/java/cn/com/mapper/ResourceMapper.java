package cn.com.mapper;

import cn.com.bean.Account;
import cn.com.bean.PResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceMapper {
    int deleteByIds(@Param("ids") Integer... ids);

    int insert(PResource record);

    PResource selectById(Integer id);

    List<PResource> findAll(PResource PResource);

    List<PResource> findByAccountId(@Param("accountId") Integer accountId);

    int update(PResource record);

    List<PResource> selectByExample(PResource resource);

    void deleteAuthorization(@Param("accountId") Integer accountId);

    void authorization(@Param("accountId") Integer accountId,@Param("resourceIds") Integer...resourceIds);
}