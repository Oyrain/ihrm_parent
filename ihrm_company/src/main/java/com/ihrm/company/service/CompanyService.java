package com.ihrm.company.service;

import com.ihrm.common.entity.Result;
import com.ihrm.domain.company.Company;

import java.util.List;

public interface CompanyService {
    /**
     * 添加企业
     * @param company
     */
    void add(Company company);

    /**
     * 更新企业
     * @param company
     */
    void update(Company company);

    /**
     * 根据id查找企业
     * @param id
     * @return
     */
    Company findById(String id);

    /**
     * 根据id删除企业
     * @param id
     */
    void deleteById(String id);

    /**
     * 查询所有企业信息
     * @return
     */
    List<Company> findAll();
}
