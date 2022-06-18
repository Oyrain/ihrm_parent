package com.ihrm.system.service;

import com.ihrm.common.entity.PageResult;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.system.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 分页查询所有用户
     * @return
     */
    public Page<User> findAll(Map<String,Object> map, int page, int pageSize);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User findById(String id);

    /**
     * 新增用户
     * @param user
     */
    public void add(User user);

    /**
     * 更新用户
     * @param user
     */
    public void update(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    public void delete(String id);
}
