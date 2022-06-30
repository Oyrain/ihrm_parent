package com.ihrm.system.controler;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.response.RoleResult;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        RoleResult roleResult = new RoleResult(role);
        return new Result(ResultCode.SUCCESS,roleResult);
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

    @PutMapping("assignPrem")
    public Result assignPerms(@RequestBody Map<String,Object> map){
        //1.获取被分配的角色id
        String roleId = (String) map.get("id");
        //2.获取到权限的id列表
        List<String> permIds = (List<String>) map.get("permIds");
        //3.调用service完成权限分配
        roleService.assignPerms(roleId,permIds);
        return new Result(ResultCode.SUCCESS);
    }

}
