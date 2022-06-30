package com.ihrm.system.service.impl;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 增加角色
     * @param role
     */
    @Override
    public void save(Role role) {
        role.setId(idWorker.nextId()+"");
        roleDao.save(role);
    }

    /**
     * 分页查询所有角色
     * @param companyId
     * @param page
     * @param size
     * @return
     */
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

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> findAll(String companyId) {
        List<Role> list = roleDao.findAll(getSpecification(companyId));
        return list;
    }

    /**
     * 根据id查找角色
     * @param id
     * @return
     */
    @Override
    public Role findById(String id) {
        return roleDao.findById(id).get();
    }

    /**
     * 修改角色
     * @param role
     */
    @Override
    public void update(Role role) {
        Role target = roleDao.getOne(role.getId());
        target.setDescription(role.getDescription());
        target.setName(role.getName());
        roleDao.save(target);
    }

    /**
     * 根据id删除角色
     * @param id
     */
    @Override
    public void deleteById(String id) {
        roleDao.deleteById(id);
    }

    /**
     * 分配权限
     * @param roleId
     * @param permIds
     */
    @Override
    public void assignPerms(String roleId, List<String> permIds) {
        //1.获取分配的角色对象
        Role role = roleDao.findById(roleId).get();
        //2.构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList =
                    permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            perms.addAll(apiList);//自定赋予API权限
            perms.add(permission);//当前菜单或按钮的权限
        }
        System.out.println(perms.size());
        //3.设置角色和权限的关系
        role.setPermissions(perms);
        //4.更新角色
        roleDao.save(role);
    }
}
