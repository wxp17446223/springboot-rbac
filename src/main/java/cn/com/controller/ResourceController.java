package cn.com.controller;

import cn.com.annotations.Authorization;
import cn.com.bean.PResource;
import cn.com.service.ResourceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resource")
public class ResourceController implements BaseController{
    @Resource
    private ResourceService resourceService;

    @GetMapping()
    @Authorization(url = "/resource",authority = "r",method = RequestMethod.GET)
    public String home(){
        return getModelName()+"/index";
    }

    @GetMapping({"/edit","/edit/{id}"})
    @Authorization(url = "/resource/edit",authority = "r",method = RequestMethod.GET)
    public String editor(@PathVariable(required = false) Integer id,@RequestParam(defaultValue = "-1") Integer aid, ModelMap modelMap){
        PResource resource=null;
        if (id != null) {
            resource = resourceService.selectById(id);
        }else {
            resource=new PResource();
            resource.setParent(this.resourceService.selectById(aid));
        }
        modelMap.addAttribute("resource",resource);
        return getModelName()+"/editor";
    }
    @ResponseBody
    @DeleteMapping
    @Authorization(url = "/resource",authority = "d",method = RequestMethod.DELETE)
    public Map<String ,Object> delete(@RequestParam("id") Integer[] ids){
        Map<String,Object> results=new HashMap<>();
        if (resourceService.deleteByIds(ids)){
            results.put("code",0);
            results.put("msg","删除成功");
        }else {
            results.put("code",1);
            results.put("msg","删除失败");
        }
        return results;
    }
    @PutMapping
    @ResponseBody
    @Authorization(url = "/resource",authority = "c",method = RequestMethod.PUT)
    public Map<String ,Object> save(PResource resource) {
        Map<String,Object> results=new HashMap<>();
        if (resource.getId()!=null){
            if (resourceService.update(resource)) {
                results.put("code",0);
                results.put("msg","修改成功");
            }else {
                results.put("code",1);
                results.put("msg","修改失败");
            }
        }else {
            if (resourceService.insert(resource)) {
                results.put("code",0);
                results.put("msg","新增成功");
            }else {
                results.put("code",1);
                results.put("msg","新增失败");
            }
        }
        return results;
    }

    @GetMapping(headers = "X-Requested-With=XMLHttpRequest")
    @ResponseBody
    @Authorization(url = "/resource",authority = "r",method = RequestMethod.GET)
    public Map<String ,Object> findAll(PResource resource){

        List<PResource> all = resourceService.findAll(resource);

        //对page进行重新定义，方便于sql语句的分页：
        Integer page=(resource.getPage()-1)*resource.getLimit();
        resource.setPage(page);
        //分页查询
        List<PResource> PResources = resourceService.selectByExample(resource);

        Map<String,Object> result=new HashMap<>();
        result.put("data",PResources);
        result.put("code",0);
        result.put("count",all.size());//数据的总条数
        result.put("msg","查询成功");
        return result;
    }

    @Override
    public String getModelName() {
        return "resource";
    }
}
