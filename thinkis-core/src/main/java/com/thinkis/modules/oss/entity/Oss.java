/**
 * Copyright &copy; 2018 thinkis All rights reserved.
 */
package com.thinkis.modules.oss.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkis.common.config.Global;
import com.thinkis.common.persistence.DataEntity;
import com.thinkis.common.utils.FileSizeHelper;
import com.thinkis.modules.sys.utils.DictUtils;

/**
 * 附件管理Entity
 * @author liuzhiping
 * @version 2018-05-09
 */
public class Oss extends DataEntity<Oss> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名称
	private String fileExt;		// 文件后缀
	private String fileLength;		// 文件大小
	private String fileId;		// 文件编号
	private String status;		// 状态
	private int download; //下载次数
	private String filePath;//文件访问路径
	
	public Oss() {
		super();
		this.status = Global.YES;
	}

	public Oss(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文件名称长度必须介于 0 和 255 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=20, message="文件后缀长度必须介于 0 和 20 之间")
	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	@Length(min=0, max=50, message="文件大小长度必须介于 0 和 50 之间")
	public String getFileLength() {
		return fileLength;
	}

	public String getFileLengthLabel(){
		return FileSizeHelper.getHumanReadableFileSize(Long.parseLong(fileLength));
	}
	
	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}
	
	@Length(min=0, max=200, message="文件编号长度必须介于 0 和 200 之间")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusLabel(){
		return DictUtils.getDictLabel(this.status,"yes_no","是");
	}
	
	public int getDownload() {
		return download;
	}

	public void setDownload(int download) {
		this.download = download;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath(){
		return Global.getConfig("fastdfs.tracker_ngnix_path") +  this.fileId;
	}
}