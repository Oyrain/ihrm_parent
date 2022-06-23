package com.ihrm.system.service.impl;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void save(Role role) {
        role.setId(idWorker.nextId()+"");
        roleDao.save(role);
    }

    @Override
    public Page<Role> findSearch(String companyId, int page, int size) {
        Specification<Role> specification = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("companyId").as(String.class),companyId);
            }
        };
        return roleDao.findAll(specification, PageRequest.of(page-1, size));
    }

    @Override
    public List<Role> findAll(String companyId) {
        List<Role> list = roleDao.findAll(getSpecification(companyId));
        return list;
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }

    @Override
    public void update(Role role) {
        Role target = roleDao.getOne(role.getId());
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.save(target);
    }

    @Override
    public void deleteById(String id) {
        roleDao.deleteById(id);
    }
}
