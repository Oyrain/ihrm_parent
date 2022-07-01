package com.ihrm.system.controler;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.common.exception.CommonException;
import com.ihrm.common.utils.JwtUtils;
import com.ihrm.domain.system.User;
import com.ihrm.domain.system.response.ProfileResult;
import com.ihrm.system.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys")
public class SystemController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;

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
            Map<String,Object> map = new HashMap<>();
            map.put("companyId",user.getCompanyId());
            map.put("companyName",user.getCompanyName());
            String token = jwtUtils.createJWT(user.getId(), user.getUsername(), map);
            return new Result(ResultCode.SUCCESS,token);
        }
    }

    /**
     * 获取个人信息
     * @param request
     * @return
     */
    @PostMapping("profile")
    public Result profile(HttpServletRequest request){
        //从请求中获取key为Authorization的头信息
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isEmpty(authorization)){
            return new Result(ResultCode.UNAUTHENTICATED);
        }
        //前后端约定头信息内容以 Bearer+空格+token 形式组成
        String token = authorization.replace("Bearer ", "");
        //比较获取claims
        Claims claims = jwtUtils.parseJWT(token);
        if(claims == null){
            throw new CommonException(ResultCode.UNAUTHENTICATED);
        }
        String userId = claims.getId();
        User user = userService.findById(userId);
        return new Result(ResultCode.SUCCESS,new ProfileResult(user));
    }
}
