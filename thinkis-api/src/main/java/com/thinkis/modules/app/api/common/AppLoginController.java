package com.thinkis.modules.app.api.common;


import com.thinkis.common.web.JwtUtils;
import com.thinkis.common.web.R;
import com.thinkis.modules.app.form.LoginForm;
import com.thinkis.modules.app.service.UserService;
import com.thinkis.modules.app.util.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author liuzhiping
 * @date 2018-4-27 22:09:28
 */
@RestController
@RequestMapping("/app")
@Api("APP登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 登录
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        String userId = userService.login(form);

        //生成token
        String token = jwtUtils.generateToken(userId);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());

        return R.ok(map);
    }

}
