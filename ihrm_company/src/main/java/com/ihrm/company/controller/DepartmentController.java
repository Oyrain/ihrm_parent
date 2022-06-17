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
        departmentService.add(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改部门
     * @param department
     * @return
     */
    @PutMapping
    public Result update(Department department){
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(String id){
        departmentService.delete(id);
        return new Result(ResultCode.SUCCESS);
    }
}
