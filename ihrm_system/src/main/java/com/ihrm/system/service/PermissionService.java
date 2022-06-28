package com.ihrm.system.service;

import com.ihrm.domain.system.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    /**
     * 1.保存权限
     */
    public void save(Map<String,Object> map);

    /**
     * 2.更新权限
     */
    public void update(Map<String,Object> map);

    /**
     * 3.根据id查询
     *     //1.查询权限
     *     //2.根据权限的类型查询资源
     *     //3.构造map集合
     */
    public Map<String, Object> findById(String id);

    /**
     * 4.查询全部
     * type : 查询全部权限列表type：0：菜单 + 按钮（权限点） 1：菜单2：按钮（权限点）3：API接口
     * enVisible : 0：查询所有saas平台的最高权限，1：查询企业的权限
     * pid ：父id
     */
    public List<Permission> findAll(Map<String, Object> map);

    /**
     * 5.根据id删除
     * //1.删除权限
     * //2.删除权限对应的资源
     *
     */
    public void deleteById(String id);


}
