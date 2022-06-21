package com.ihrm.system.service;

import com.ihrm.domain.system.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    /**
     * 增加角色
     * @param role
     */
    public void save(Role role);

    /**
     * 分页查询所有角色
     * @param companyId
     * @param page
     * @param size
     * @return
     */
    public Page findSearch(String companyId,int page,int size);

    /**
     * 添加角色
     * @param id
     * @return
     */
    public Role findById(String id);

    /**
     * 修改角色
     * @param role
     */
    public void update(Role role);

    /**
     * 根据id删除角色
     * @param id
     */
    public void deleteById(String id);

}
