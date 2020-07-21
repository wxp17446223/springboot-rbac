package cn.com.controller;

import cn.com.annotations.Authorization;
import cn.com.bean.Account;
import cn.com.bean.PResource;
import cn.com.service.AccountService;
import cn.com.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/account")
@Slf4j
public class AccountController implements BaseController{
    @Resource
    private AccountService accountService;
    @Resource
    private ResourceService resourceService;

    @GetMapping()
    @Authorization(url = "/account",authority = "r",method = RequestMethod.GET)
    public String home(){
        return getModelName()+"/index";
    }

    @GetMapping({"/edit","/edit/{id}"})
    @Authorization(url = "/account/edit",authority = "r",method = RequestMethod.GET)
    public String editor(@PathVariable(required = false) Integer id, ModelMap modelMap){
        if (id != null) {
            Account account = accountService.findById(id);
            modelMap.addAttribute("account",account);
        }
        return getModelName()+"/editor";
    }

    @GetMapping("/authorization/{id}")
    @Authorization(url = "/account/authorization",authority = "r",method = RequestMethod.GET)
    public String authorization(@PathVariable Integer id, Model model){
        model.addAttribute("id",id);
        return getModelName()+"/authorization";
    }

    @PutMapping("/authorization")
    @ResponseBody
    @Authorization(url = "/account/authorization",authority = "c",method = RequestMethod.PUT)
    public Map<String ,Object> authorization(@RequestParam Integer id,@RequestParam(value = "resourceIds[]",required = false) Integer[] resourceIds){
        Map<String,Object> results=new HashMap<>();
        log.info("账号id{}",id);
        try{
            this.resourceService.authorization(id,resourceIds);
            results.put("code",0);
            results.put("msg","授权成功");
        }catch (Exception e){
            results.put("code",1);
            results.put("msg","授权失败:"+e.getMessage());
        }
        return results;
    }

    @GetMapping(path = "/authorization/{id}",headers = "X-Requested-With=XMLHttpRequest")
    @ResponseBody
    @Authorization(url = "/account/authorization",authority = "r",method = RequestMethod.GET)
    public Map<String ,Object> authorization(@PathVariable Integer id){
        Map<String,Object> results=new HashMap<>();
        List<PResource> resources = resourceService.findAll(null);
        List<PResource> accountResource = resourceService.findByAccountId(id);
        results.put("code",0);
        results.put("msg","查询成功");

        Map<String,Object> data=new HashMap<>();

        data.put("list",resources);

        List<Integer> ids = accountResource.stream().map(res -> res.getId()).collect(Collectors.toList());

        data.put("checkedId",ids);

        results.put("data",data);

        return results;
    }

    @ResponseBody
    @DeleteMapping
    @Authorization(url = "/account",authority = "d",method = RequestMethod.DELETE)
    public  Map<String ,Object> delete(@RequestParam("id") Integer[] ids){
        Map<String,Object> results=new HashMap<>();
        if (accountService.deleteByIds(ids)){
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
    @Authorization(url = "/account",authority = "c,u",method = RequestMethod.PUT)
    public Map<String ,Object> save(Account account) {
        Map<String,Object> results=new HashMap<>();
        if (account.getId()!=null){
            if (accountService.updateAccount(account)) {
                results.put("code",0);
                results.put("msg","修改成功");
            }else {
                results.put("code",1);
                results.put("msg","修改失败");
            }
        }else {
            if (accountService.insertAccount(account)) {
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
    @Authorization(url = "/account",authority = "r",method = RequestMethod.GET)
    public Map<String ,Object> findAll(Account account){
        List<Account> all = accountService.findAll(account);

        Integer page=(account.getPage()-1)*account.getLimit();
        account.setPage(page);

        List<Account> accounts = accountService.selectByExample(account);

        Map<String,Object> result=new HashMap<>();
        result.put("data",accounts);
        result.put("code",0);
        result.put("count",all.size());
        result.put("msg","查询成功");
        return result;
    }

    @Override
    public String getModelName() {
        return "account";
    }
}
