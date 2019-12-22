package com.thinkis.modules.app.util;

import com.thinkis.common.utils.StringUtils;
import com.thinkis.modules.app.exception.AppException;

/**
 * 数据校验
 * @author liuzhiping
 * @date 2018-4-27 23:12:04
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new AppException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new AppException(message);
        }
    }
}
