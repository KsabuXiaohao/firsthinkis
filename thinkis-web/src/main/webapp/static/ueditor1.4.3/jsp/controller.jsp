<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.thinkis.common.config.Global" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	String action = request.getParameter("action");

	String result = new ActionEnter( request, rootPath ).exec();

	JSONObject resultObj = JSONObject.parseObject(result);

	resultObj.put("catcherUrlPrefix", request.getContextPath());

	String userId = request.getUserPrincipal().toString();

	resultObj.put("imageManagerUrlPrefix", request.getContextPath());
	resultObj.put("fileManagerUrlPrefix", request.getContextPath());
	result = resultObj.toJSONString();
	if( action!=null && (action.equals("listfile") || action.equals("listimage") ) ){
		rootPath = rootPath.replace("\\", "/");
		result = result.replaceAll(rootPath, "/");
	}

	out.write( result );

%>