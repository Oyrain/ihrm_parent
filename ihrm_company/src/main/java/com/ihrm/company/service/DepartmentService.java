package com.ihrm.company.service;

import com.ihrm.domain.company.Department;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {

    /**
     * 查询所有部门
     * @return
     */
    public List<Department> findAll();

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    public Department findById(String id);

    /**
     * 新增部门
     * @param department
     */
    public void add(Department department);

    /**
     * 修改部门
     * @param department
     */
    public void update(Department department);

    /**
     * 删除部门
     * @param id
     */
    public void delete(String id);
}
