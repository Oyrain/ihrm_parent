package com.ihrm.company.service.impl;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;

    @Override
    public List<Department> findAll() {
        //根据企业id查询部门
        String companyId = "1";
        Specification<Department> specification = new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("companyId").as(String.class),companyId);
            }
        };
        return departmentDao.findAll();
    }

    @Override
    public Department findById(String id) {
        return departmentDao.findById(id).get();
    }

    @Override
    public void add(Department department) {
        department.setId(idWorker.nextId()+"");
        departmentDao.save(department);
    }

    @Override
    public void update(Department department) {
        Department depart = departmentDao.findById(department.getId()).get();
        depart.setName(department.getName());
        depart.setPid(department.getPid());
        depart.setManagerId(department.getManagerId());
        depart.setIntroduce(department.getIntroduce());
        depart.setManager(department.getManager());
        departmentDao.save(depart);
    }

    @Override
    public void delete(String id) {
        departmentDao.deleteById(id);
    }
}
