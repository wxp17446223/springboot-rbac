package cn.com.service;

import cn.com.bean.PResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceService{


    void authorization(Integer accountId,Integer...resourceIds);

    boolean deleteByIds(@Param("ids") Integer... ids);

    boolean insert(PResource record);

    PResource selectById(Integer id);

    List<PResource> findAll(PResource PResource);

    List<PResource> findByAccountId(Integer accountId);

    boolean update(PResource record);

    List<PResource> selectByExample(PResource resource);

}
