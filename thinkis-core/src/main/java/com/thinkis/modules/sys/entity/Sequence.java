/**
 * Copyright &copy; 2012-2016 <a href="http://thinkis.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.sys.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.thinkis.modules.sys.utils.DictUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkis.common.persistence.DataEntity;

/**
 * 序列键管理Entity
 * @author liuzhiping
 * @version 2017-08-01
 */
public class Sequence extends DataEntity<Sequence> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 键值名称
	private Integer currentValue;		// 初始值
	private Integer incrementValue;		// 键值递增
	private Integer uuidType;		// 键值类型
	private Integer keyLenType;		// 长度类型
	private Integer keyLen;		// 序列键长度
	private Integer keyFixType;		// 修饰类型
	private String keyFix;		// 序列键修饰
	private Integer keyDateType;		// 序列日期
	private Integer keyDateLoop;		// 按日期循环增加
	private Integer keyDateLoopInit;		// 循环初值
	private Integer keyDateFormat;		// 日期序列
	private Date keyDate;		// 上次序列开始时间
	
	private String example;//序列键示例
	
	public Sequence() {
		super();
		this.currentValue = 1;
		this.incrementValue = 1;
		this.uuidType = 0;
		this.keyLenType = 0;
		this.keyLen = 4;
		this.keyFixType = 0;
		this.keyFix = "KEY";
		this.keyDateType = 0;
		this.keyDateLoop = 0;
		this.keyDateLoopInit = 1;
		this.keyDateFormat = 0;
	}

	public Sequence(String id){
		super(id);
	}

	@Length(min=1, max=40, message="键值名称长度必须介于 1 和 40 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}
	
	public Integer getIncrementValue() {
		return incrementValue;
	}

	public void setIncrementValue(Integer incrementValue) {
		this.incrementValue = incrementValue;
	}
	
	public Integer getUuidType() {
		return uuidType;
	}

	public void setUuidType(Integer uuidType) {
		this.uuidType = uuidType;
	}
	
	public Integer getKeyLenType() {
		return keyLenType;
	}

	public void setKeyLenType(Integer keyLenType) {
		this.keyLenType = keyLenType;
	}
	
	public Integer getKeyLen() {
		return keyLen;
	}

	public void setKeyLen(Integer keyLen) {
		this.keyLen = keyLen;
	}
	
	public Integer getKeyFixType() {
		return keyFixType;
	}

	public void setKeyFixType(Integer keyFixType) {
		this.keyFixType = keyFixType;
	}
	
	@Length(min=0, max=20, message="序列键修饰长度必须介于 0 和 20 之间")
	public String getKeyFix() {
		return keyFix;
	}

	public void setKeyFix(String keyFix) {
		this.keyFix = keyFix;
	}
	
	public Integer getKeyDateType() {
		return keyDateType;
	}

	public void setKeyDateType(Integer keyDateType) {
		this.keyDateType = keyDateType;
	}
	
	public Integer getKeyDateLoop() {
		return keyDateLoop;
	}

	public void setKeyDateLoop(Integer keyDateLoop) {
		this.keyDateLoop = keyDateLoop;
	}
	
	public Integer getKeyDateLoopInit() {
		return keyDateLoopInit;
	}

	public void setKeyDateLoopInit(Integer keyDateLoopInit) {
		this.keyDateLoopInit = keyDateLoopInit;
	}
	
	public Integer getKeyDateFormat() {
		return keyDateFormat;
	}

	public void setKeyDateFormat(Integer keyDateFormat) {
		this.keyDateFormat = keyDateFormat;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getKeyDate() {
		return keyDate;
	}

	public void setKeyDate(Date keyDate) {
		this.keyDate = keyDate;
	}
	
	//生成示例
	public String getExample(){
		StringBuffer sb = new StringBuffer(64);
		if(this.uuidType == 0){
			if(this.keyFixType == 15){//前缀
				sb.append(this.keyFix);
			}
			if(this.keyDateType == 15){//日期序列
				if(this.keyDateLoop == 15){
					this.currentValue = this.keyDateLoopInit;
				}
				DateFormat today = null;
				if(this.keyDateFormat == 0){
					today = new SimpleDateFormat("yyyyMMdd");
				}else if(this.keyDateFormat == 15){
					today = new SimpleDateFormat("yyyyMM");
				}else if(this.keyDateFormat == 255){
					today = new SimpleDateFormat("yyyy");
				}
				sb.append(today.format(new Date()));
			}

			if(this.keyLenType == 15){//限制长度
				sb.append(String.format("%0" + this.keyLen + "d", this.currentValue));
			}else{
				sb.append(this.currentValue);
			}
			if(this.keyFixType == 255){//后缀
				sb.append(this.keyFix);
			}
		}else if(this.uuidType == 15){
			UUID uuid = UUID.randomUUID();
			sb.append(uuid.getLeastSignificantBits());
		}else if(this.uuidType == 255){
			UUID uuid = UUID.randomUUID();
			sb.append(uuid.toString());
		}
		return sb.toString();
	}

	public String getUuidTypeLabel() {
		return DictUtils.getDictLabel(String.valueOf(this.uuidType),"uuid_type","");
	}

	public String getKeyLenTypeLabel() {
		return DictUtils.getDictLabel(String.valueOf(this.keyLenType),"key_len_type","");
	}

	public String getKeyFixTypeLabel() {
		return DictUtils.getDictLabel(String.valueOf(this.keyFixType),"key_fix_type","");
	}

	public String getKeyDateTypeLabel() {
		return DictUtils.getDictLabel(String.valueOf(this.keyDateType),"key_date_type","");
	}

	public String getKeyDateLoopLabel() {
		return DictUtils.getDictLabel(String.valueOf(this.keyDateLoop),"key_date_loop","");
	}

	public String getKeyDateFormatLabel() {
		return DictUtils.getDictLabel(String.valueOf(this.keyDateFormat),"key_date_format","");
	}
}