package com.ihrm.common.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共controller
 *     获取request，response
 *     获取企业id，获取企业名称
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    //企业id,暂时设为 1,以后在动态获取
    public String parseCompanyId(){
        return "1";
    }

    //企业名称,暂时设为固定值,以后在动态获取
    public String parseCompanyName(){
        return "江苏传智播客教育股份有限公司";
    }
}
