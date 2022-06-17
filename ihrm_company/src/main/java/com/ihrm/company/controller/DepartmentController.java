package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    /**
     * 组织架构列表
     * @return
     */
    @GetMapping
    public Result findAll(){
        Company company = companyService.findById(parseCompanyId());
        //根据企业id查询部门
        List<Department> list = departmentService.findAll(parseCompanyId());
        return new Result(ResultCode.SUCCESS,new DeptListResult(company,list));
    }

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Department department){
        //1.设置保存的企业id
        /**
         * 企业id：目前使用固定值1，以后会解决
         */
        department.setCompanyId(parseCompanyId());
        //2.调用service完成保存企业
        departmentService.add(department);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改部门
     * @param department
     * @return
     */
    @PutMapping("{id}")
    public Result update(@PathVariable("id") String id,@RequestBody Department department){
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id){
        departmentService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}
