package cn.com.interceptor;

import cn.com.annotations.Authorization;
import cn.com.bean.Account;
import cn.com.bean.PResource;
import cn.com.consts.SystemConst;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Component
/**
 * 拦截器权限的鉴定
 */
public class SecurityInterceptor implements HandlerInterceptor, SystemConst {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute(LOGIN_STATUS);
        if (account==null){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        if (handler instanceof HandlerMethod){
            /**
             * 先获得权限信息，再通过session获得登录用户信息
             */
            HandlerMethod handlerMethod=(HandlerMethod)handler;
            Authorization annotation = handlerMethod.getMethodAnnotation(Authorization.class);
            if (annotation==null){
                return true;
            }

            //获得执行方法上的权限url
            String[] urls= (String[]) AnnotationUtils.getValue(annotation,"url");
            RequestMethod[] methods = (RequestMethod[]) AnnotationUtils.getValue(annotation, "method");
            String authoritys = (String) AnnotationUtils.getValue(annotation, "authority");

            List<PResource> resources = account.getResources();

            //数据结构转换
            Map<String,List<PResource>> resourceMap=new HashMap<>();
            for (PResource resource : resources) {
                if (!resourceMap.containsKey(resource.getUrl())){
                    resourceMap.put(resource.getUrl(),new ArrayList<>());
                }
                resourceMap.get(resource.getUrl()).add(resource);
            }
            for (String url : urls) {
                if (resourceMap.containsKey(url)){
                    List<PResource> list = resourceMap.get(url);

                    for (PResource resource : list) {
                        //鉴定url是否匹配
                        if (url.equals(resource.getUrl())){
                            //鉴定访问方式是否匹配
                            if (hasAuthority(RequestMethod.valueOf(request.getMethod()), methods)) {
                                //鉴定访问权限是否匹配
                                if (hasAuthority(authoritys,resource)){
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        response.sendError(403,"您无权访问该功能");
        return false;
    }

    private boolean hasAuthority(String authoritys, PResource resource) {
        if (authoritys == null|| authoritys.isEmpty()) {
            return true;
        }
        String authority = resource.getAuthority();
        List<String> list = Arrays.asList(authority.split(","));
        List<String> list1 = Arrays.asList(authoritys.split(","));
        //有交集返回false
        return Collections.disjoint(list,list1)==false;
    }

    private boolean hasAuthority(RequestMethod method, RequestMethod[] requestMethods) {
        if (requestMethods.length>0){
            for (RequestMethod requestMethod : requestMethods) {
                if (requestMethod==method){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
