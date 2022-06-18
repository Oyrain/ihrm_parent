package com.ihrm.system.controler;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.PageResult;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;;
import com.ihrm.domain.system.User;
import com.ihrm.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
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
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
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
}
