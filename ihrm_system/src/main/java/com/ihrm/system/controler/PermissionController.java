package com.ihrm.system.controler;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/sys/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 保存
     * @param map
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Map<String,Object> map){
        permissionService.save(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改
     * @param id
     * @param map
     * @return
     */
    @PutMapping("{id}")
    public Result update(@PathVariable String id,@RequestBody Map<String,Object> map){
        //构造id
        map.put("id",id);
        permissionService.update(map);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询列表
     * @param map
     * @return
     */
    @GetMapping
    public Result findAll(@RequestParam Map map){
        List list = permissionService.findAll(map);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
        Map<String, Object> map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 分局id删除
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable String id){
        permissionService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
}
