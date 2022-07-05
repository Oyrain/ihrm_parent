package com.ihrm.system.controler;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.system.service.PermissionService;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class SystemController extends BaseController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 用户登录
     * 1.通过 service根据 mobile查询用户
     * 2.比较 password
     * 3.生成 jwt信息
     * @param loginMap
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody Map<String,String> loginMap){
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        User user = userService.findByMobile(mobile);
        if(user == null || !user.getPassword().equals(password)){
            //登录失败
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }else {
            //登录成功
            //api权限字符串
            StringBuilder sb = new StringBuilder();
            //获取到所有的可访问API权限
            for (Role role : user.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    if(perm.getType() == PermissionConstants.PY_API) {
                        sb.append(perm.getCode()).append(",");
                    }
                }
            }

            Map<String,Object> map = new HashMap<>();
            map.put("apis",sb.toString());//可访问的api权限字符串
            map.put("companyId",user.getCompanyId());
            map.put("companyName",user.getCompanyName());
            String token = jwtUtils.createJWT(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS,token);
        }
    }

    /**
     * 获取个人信息
     * 用户登录成功之后，获取用户信息
     * 1.获取用户id
     * 2.根据用户id查询用户
     * 3.构建返回值对象
     * 4.响应
     * @param request
     * @return
     */
    @PostMapping("profile")
    public Result profile(HttpServletRequest request){

        String userId = claims.getId();
        //获取用户信息
        User user = userService.findById(userId);
        ProfileResult result = null;

        //根据不同的用户级别获取用户权限
        if("user".equals(user.getLevel())){
            return new Result(ResultCode.SUCCESS,new ProfileResult(user));
        }else {
            Map map = new HashMap();
            if("coAdmin".equals(user.getLevel())){
                map.put("enVisible","1");
            }
            List<Permission> list = permissionService.findAll(map);
            result = new ProfileResult(user,list);
        }
        return new Result(ResultCode.SUCCESS,result);
    }
}
