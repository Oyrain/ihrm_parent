package com.ihrm.common.controller;

import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
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
    protected Claims claims;

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.response = response;
        Object obj = request.getAttribute("user_claims");

        if(obj != null){
            this.claims = (Claims) obj;
        }
    }

    //企业id,暂时设为 1,以后在动态获取
    public String parseCompanyId(){
        Object companyId = this.claims.get("companyId");
        if(StringUtils.isEmpty(companyId)){
            return "1";
        }
        return (String) companyId;
    }

    //企业名称,暂时设为固定值,以后在动态获取
    public String parseCompanyName(){
        Object companyName = this.claims.get("companyName");
        if(StringUtils.isEmpty(companyName)){
            return "江苏传智播客教育股份有限公司";
        }
        return (String) this.claims.get("companyName");
    }
}
