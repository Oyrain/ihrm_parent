package com.ihrm.system.controler;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.UserResult;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询所有用户
     * @return
     */
    @GetMapping
    public Result findAll(int page, int size, @RequestParam Map map){
        //设置当前的企业id
        map.put("companyId",parseCompanyId());
        Page<User> userPage  = userService.findAll(map,page,size);

        return new Result(ResultCode.SUCCESS,new PageResult<>(userPage.getTotalElements(),userPage.getContent()));
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
        // 添加 roleIds
        User user = userService.findById(id);
        UserResult userResult = new UserResult(user);
        return new Result(ResultCode.SUCCESS,userResult);
    }

    /**
     * 添加部门
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user){
        //1.设置用户的企业id
        /**
         * 企业id：目前使用固定值1，以后会解决
         */
        user.setCompanyId(parseCompanyId());
        user.setCompanyName(parseCompanyName());
        //2.调用service完成保存用户
        userService.add(user);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PutMapping("{id}")
    public Result update(@PathVariable("id") String id,@RequestBody User user){
        user.setId(id);
        userService.update(user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id){
        userService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 分配角色
     * @param map
     * @return
     */
    @PutMapping("assignRoles")
    public Result assignRoles(@RequestBody Map<String,Object> map){
        //1.获取被分配的用户id
        String userId = (String) map.get("id");
        //2.获取到角色id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //3.调用service完成角色分配
        userService.assignRoles(userId,roleIds);
        return new Result(ResultCode.SUCCESS);
    }
}
