package cn.com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
    * 账号信息表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    /**
    * 账号标识符
    */
    private Integer id;

    /**
    * 登录账号
    */
    private String account;

    /**
    * 登录密码
    */
    private String password;
    /**
     * 查询登录用户的权限信息
     */
    private List<PResource> resources;
    /**
     * 第几页码
     */
    private Integer page;
    /**
     * 显示条数
     */
    private Integer limit;
}