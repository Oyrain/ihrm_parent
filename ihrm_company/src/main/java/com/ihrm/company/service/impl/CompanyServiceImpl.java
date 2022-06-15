package com.ihrm.company.service.impl;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.CompanyDao;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void add(Company company) {
        company.setId(idWorker.nextId()+"");
        company.setCreateTime(new Date());
        company.setAuditState("0"); //0:未审核 1:已审核
        company.setState(1); //0:未启用 1：已启用
        companyDao.save(company);
    }

    @Override
    public void update(Company company) {
        companyDao.save(company);
    }

    @Override
    public Company findById(String id) {
        Company company = companyDao.findById(id).get();
        return company;
    }

    @Override
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    @Override
    public List<Company> findAll() {
        List<Company> list = companyDao.findAll();
        return list;
    }
}
