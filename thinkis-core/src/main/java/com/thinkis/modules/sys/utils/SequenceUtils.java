package com.thinkis.modules.sys.utils;

import com.thinkis.common.utils.SpringContextHolder;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.modules.sys.dao.SequenceDao;

/**
 * 序列键工具类
 * @author liuzhiping
 * @version 2017年8月1日
 */
public class SequenceUtils {
	
	private static SequenceDao sequenceDao = SpringContextHolder.getBean(SequenceDao.class);
	
	/**
	 * 根据键名称，获取键值
	 * @param keyName 键名
	 * @return 键值
	 */
	public static String getKey(String keyName){
		if(StringUtils.isEmpty(keyName)){
			return null;
		}else{
			return sequenceDao.getKey(keyName);
		}
	}

}
