package com.thinkis.modules.app.exception;

import com.thinkis.common.web.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 * 
 * @author liuzhiping
 * @date 2018年4月16日20:24:11
 */
@ControllerAdvice(basePackages = "com.thinkis.modules.app")
public class AppExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(AppException.class)
	@ResponseBody
	public R handleRRException(AppException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());
		return r;
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	public R handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseBody
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
 	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
