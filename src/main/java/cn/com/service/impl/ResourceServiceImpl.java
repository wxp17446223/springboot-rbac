package cn.com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.com.mapper.ResourceMapper;
import cn.com.bean.PResource;
import cn.com.service.ResourceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Resource
    private ResourceMapper resourceMapper;

    @Transactional
    @Override
    public void authorization(Integer accountId, Integer[] resourceIds) {
        //清除授权
        this.resourceMapper.deleteAuthorization(accountId);

        if (resourceIds!=null) {
            //重新授权
            this.resourceMapper.authorization(accountId, resourceIds);
        }
    }

    @Override
    public boolean deleteByIds(Integer... ids) {
        return resourceMapper.deleteByIds(ids)>0;
    }

    @Override
    public boolean insert(PResource record) {
        return resourceMapper.insert(record)>0;
    }

    @Override
    public PResource selectById(Integer id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public List<PResource> findAll(PResource pResource) {
        return resourceMapper.findAll(pResource);
    }

    @Override
    public List<PResource> findByAccountId(Integer accountId) {
        return resourceMapper.findByAccountId(accountId);
    }

    @Override
    public boolean update(PResource record) {
        return resourceMapper.update(record)>0;
    }

    @Override
    public List<PResource> selectByExample(PResource resource) {
        return resourceMapper.selectByExample(resource);
    }
}
