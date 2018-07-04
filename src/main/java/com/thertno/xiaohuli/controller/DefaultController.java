package com.thertno.xiaohuli.controller;

import com.thertno.xiaohuli.bean.ResponseResult;
import com.thertno.xiaohuli.common.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;

/**
 * TODO:DOCUMENT ME!
 *
 * @author penglei
 * @date 2018/6/13
 */
@Controller
@Validated
@RequestMapping("/login")
public class DefaultController {

    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object giftProduct(@NotBlank(message = "用户名不能为空") String usr,
            @NotBlank(message = "密码不能为空") String passwd) {
        if (!usr.equals(passwd)) {
            throw new ServiceException("账户密码错误");
        }
        return new ResponseResult("登录成功").success();
    }
}
