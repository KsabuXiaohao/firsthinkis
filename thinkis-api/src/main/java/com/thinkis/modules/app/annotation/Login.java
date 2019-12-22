package com.thinkis.modules.app.annotation;

import java.lang.annotation.*;

/**
 * app登录效验
 * @author liuzhiping
 * @date 2018年4月16日18:53:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
