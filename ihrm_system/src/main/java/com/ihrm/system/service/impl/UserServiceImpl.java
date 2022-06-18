package com.ihrm.system.service.impl;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 分页查询所有用户
     * @return
     */
    @Override
    public Page<User> findAll(Map<String,Object> map,int page,int pageSize) {
        //1.需要查询条件
        Specification<User> specification = new Specification<User>() {
            /**
             * 动态拼接查询条件
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                //根据请求的companyId是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                //根据请求的部门id是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class),(String)map.get("departmentId")));
                }
                //根据请求的hasDept判断,是否分配部门 0未分配(hasDept == null) 1已分配(hasDept != null)
                if(StringUtils.isEmpty(map.get("hasDept")) || "0".equals((String)map.get("hasDept"))){
                    //已分配
                    list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                }else {
                    //未分配部门
                    list.add(criteriaBuilder.isNull(root.get("departmentId")));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        //2.分页
        Page<User> userPage = userDao.findAll(specification, new PageRequest(page-1, pageSize));
        return userPage;
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 新增用户
     * @param user
     */
    @Override
    public void add(User user) {
        user.setId(idWorker.nextId()+"");
        user.setCreateTime(new Date());//设置创建时间
        user.setPassword("123456");//设置默认密码
        user.setEnableState(1);//设置状态
        userDao.save(user);
    }

    /**
     * 更新用户
     * @param user
     */
    @Override
    public void update(User user) {
        User target = userDao.getOne(user.getId());
        target.setPassword(user.getPassword());
        target.setUsername(user.getUsername());
        target.setEnableState(user.getEnableState());
        target.setMobile(user.getMobile());
        target.setDepartmentId(user.getDepartmentId());
        target.setDepartmentName(user.getDepartmentName());
        userDao.save(target);
    }

    /**
     * 根据id删除用户
     * @param id
     */
    @Override
    public void delete(String id) {
        userDao.deleteById(id);
    }
}
