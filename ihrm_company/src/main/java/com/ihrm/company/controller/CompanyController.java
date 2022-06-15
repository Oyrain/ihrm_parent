package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 查询所有企业
     * @return
     */
    @GetMapping
    public Result findAll(){
        List<Company> list = companyService.findAll();
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 添加企业
     * @param company
     * @return
     */
    @PostMapping()
    public Result add(@RequestBody Company company){
        companyService.add(company);
        return Result.SUCCESS();
    }

    /**
     * 根据id获取企业
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result findById(@PathVariable("id") String id){
        Company company = companyService.findById(id);
        return new Result(ResultCode.SUCCESS,company);
    }

    /**
     * 更新企业
     * @param company
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Company company){
        companyService.update(company);
        return Result.SUCCESS();
    }

    /**
     * 删除企业
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id){
        companyService.deleteById(id);
        return Result.SUCCESS();
    }
}
