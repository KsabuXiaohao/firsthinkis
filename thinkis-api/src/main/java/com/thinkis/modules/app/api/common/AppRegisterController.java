package com.thinkis.modules.app.api.common;


import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Validator;
import com.google.common.collect.Lists;
import com.thinkis.common.web.R;
import com.thinkis.modules.app.form.RegisterForm;
import com.thinkis.modules.app.util.ValidatorUtils;
import com.thinkis.modules.sys.entity.Office;
import com.thinkis.modules.sys.entity.Role;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 注册
 * @author liuzhiping
 * @date 2018年4月17日14:45:45
 */
@RestController
@RequestMapping("${apiPath}/v1/api")
@Api("APP注册接口")
public class AppRegisterController {
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ApiOperation(value = "注册")
    public R register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        if(!Validator.isMobile(form.getMobile())){
            return R.error("注册失败，无效的手机号");
        }
        if (systemService.getUserByLoginName(form.getMobile()) != null){
            return R.error("注册失败，手机号已存在");
        }

        User user = new User();
        user.setCompany(new Office("1"));
        user.setOffice(new Office("2"));
        user.setLoginName(form.getMobile());
        user.setPassword(SystemService.entryptPassword(form.getPassword()));
        user.setName(form.getName());
        user.setMobile(form.getMobile());
        user.setUserType("3");
        user.setNo("001");
        user.setCreateBy(new User("1"));
        user.setUpdateBy(new User("1"));

        // 角色数据有效性验证，过滤不在授权内的角色
        List<Role> roleList = Lists.newArrayList();
        Role role = new Role("6");//普通用户角色
        roleList.add(role);
        user.setRoleList(roleList);

        // 保存用户信息
        systemService.saveUser(user);
        Console.log("注册成功");

        return R.ok();
    }
}
