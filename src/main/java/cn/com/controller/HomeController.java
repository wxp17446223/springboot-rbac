package cn.com.controller;

import cn.com.annotations.Authorization;
import cn.com.bean.Account;
import cn.com.bean.PResource;
import cn.com.consts.SystemConst;
import cn.com.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({SystemConst.LOGIN_STATUS,SystemConst.MENUS})
public class HomeController {
    @Resource
    private AccountService accountService;

    @GetMapping("/")
    @Authorization(url = "/",authority = "r",method = RequestMethod.GET)
    public String home() {
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String login(Account account, Model model) {
        List<Account> all = accountService.findAll(account);
        if (all.size()==1){
            account = all.get(0);
            List<PResource> resources = account.getResources();
            //List<PResource> menus = resources.stream().filter(res -> res.getType() == 1).collect(Collectors.toList());
            //aid  子菜单集合
            Map<Integer,List<PResource>> menus=new HashMap<>();
            for (PResource resource : resources) {
                if (resource.getType()==1){
                    if (!menus.containsKey(resource.getAid())){
                        menus.put(resource.getAid(),new ArrayList<>());
                    }
                    menus.get(resource.getAid()).add(resource);
                }
            }
            model.addAttribute(SystemConst.MENUS,menus);
            model.addAttribute(SystemConst.LOGIN_STATUS,account);

        }else {
            return "login";
        }
        return "redirect:/";
    }
    @GetMapping("/logout")
    public String loginOut(SessionStatus status){
        status.setComplete();
        return "login";
    }
}
