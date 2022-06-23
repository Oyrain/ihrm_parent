package com.ihrm.system.controler;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Role role){
        roleService.save(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id更新角色
     * @param id
     * @param role
     * @return
     */
    @PutMapping("{id}")
    public Result update(@PathVariable("id") String id,@RequestBody Role role){
        roleService.update(role);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id){
        roleService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable("id") String id){
        Role role = roleService.findById(id);
        return new Result(ResultCode.SUCCESS,role);
    }

    @GetMapping()
    public Result findByPage(int page,int pagesize,Role role){
        Page<Role> rolePage = roleService.findSearch(parseCompanyId(), page, pagesize);
        PageResult pageResult = new PageResult<>(rolePage.getTotalElements(),rolePage.getContent());
        return new  Result(ResultCode.SUCCESS,pageResult);
    }

    @GetMapping("list")
    public Result findAll(){
        List<Role> list = roleService.findAll(parseCompanyId());
        return new Result(ResultCode.SUCCESS,list);
    }

}
