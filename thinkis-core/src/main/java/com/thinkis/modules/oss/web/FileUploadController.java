/**
 * Copyright &copy; 2012-2016 <a href="http://szmkst.com">Mini</a> All rights reserved.
 */
package com.thinkis.modules.oss.web;

import com.thinkis.common.config.Global;
import com.thinkis.common.mapper.JsonMapper;
import com.thinkis.common.utils.FileUtils;
import com.thinkis.common.utils.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.oss.entity.Oss;
import com.thinkis.modules.oss.service.OssService;
import com.thinkis.modules.sys.utils.UserUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "${adminPath}/file")
public class FileUploadController extends BaseController {

    @Autowired
    OssService ossService;
  
    private String savePath;// 数据库存储路径

    private String uploadPath;// 附件的实际存储的绝对路径

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, FileUploadException {
          
        Calendar date = Calendar.getInstance();  
        response.setCharacterEncoding("UTF-8");  
        String tempFileName = null; /* 临时文件名 */
        String newFileName = null; /* 最后合并后的新文件名 */  
        BufferedOutputStream outputStream = null;  
        String filename=request.getParameter("filename");// 修改文件名
        String type = request.getParameter("type"); //文件类型
        if(StringUtils.isBlank(type)){
            Result result = ResultGenerator.genFailResult("文件类型不能为空");
            response.getWriter().write(JsonMapper.toJsonString(result));
            return;
        }
        String filePath = request.getParameter("uploadPath"); //文件上传目录
        if(StringUtils.isBlank(filePath)){
            filePath = "files";
        }

        //文件保存路径
        savePath = Global.USERFILES_BASE_URL + "/" + UserUtils.getUser().getId() + "/"
                + type + filePath + "/"
                + date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH) + 1) + "/"
                + date.get(Calendar.DAY_OF_MONTH);

        //在磁盘上创建文件上传目录
        String uploadPath = Global.getUserfilesBaseDir() + savePath;
        FileUtils.createDirectory(FileUtils.path(uploadPath));
        Map<String, Object> params = new HashMap<String, Object>();
        if (ServletFileUpload.isMultipartContent(request)) {  
            try {  
                DiskFileItemFactory factory = new DiskFileItemFactory();  
                factory.setSizeThreshold(1024);  
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");  

                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                response.setContentType("text/html; charset=UTF-8");
                for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                    MultipartFile mf = entity.getValue();
                    tempFileName = mf.getOriginalFilename();
                    if(StringUtils.isNotEmpty(filename)){
                        filename=URLDecoder.decode( request.getParameter("filename"), "utf-8");
                        newFileName =filename.concat(".").concat(FilenameUtils.getExtension(tempFileName));
                    }else{
                        newFileName = tempFileName;
                    }
                    File uploadFile = new File(uploadPath, newFileName);
                    FileCopyUtils.copy(mf.getBytes(), uploadFile);

                    //保存上传文件信息到数据库
                    Oss oss = new Oss();
                    oss.setFileName(newFileName);
                    oss.setFileExt(FilenameUtils.getExtension(tempFileName));
                    oss.setFileLength(String.valueOf(mf.getSize()));
                    oss.setFileId(savePath + "/" + newFileName);
                    oss.setStatus(Global.YES);
                    oss.setDownload(0);
                    oss.setRemarks("普通方式上传");
                    ossService.save(oss);

                    String fileUrl = request.getContextPath()+ savePath + "/" + newFileName;
                    params.put("state", "SUCCESS");
                    params.put("url", fileUrl);
                    params.put("size", mf.getSize());
                    params.put("original", newFileName);
                    params.put("type", mf.getContentType());
                }
                response.getWriter().write(JsonMapper.toJsonString(params));
            } catch (Exception e) {  
                e.printStackTrace();
                params.put("state", "ERROR");
                params.put("msg", "上传失败");
                response.getWriter().write(JsonMapper.toJsonString(params));
            } finally {  
                try {  
                    if (outputStream != null)                  	
                        outputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}  