package cn.com.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
    * 资源信息表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PResource {
    /**
    * 资源标识符
    */
    private Integer id;

    /**
     * 父资源id，如果id资源为-1，则为顶级节点(没有父节点)
     */
    private Integer aid;

    /**
    * 资源名称
    */
    private String name;

    /**
    * 资源URL

    */
    private String url;

    /**
     * 第几页码
     */
    private Integer page;

    /**
     * 显示条数
     */
    private Integer limit;

    /**
     * 权限信息
     */
    private String authority;

    /**
     * 资源类型：1：菜单 0：功能
     */
    private Integer type;

    /**
     * 字资源列表
     */
    private List<PResource> children;

    /**
     * 父资源
     */
    private PResource parent;
}