package com.thinkis.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;

/**
 * 桌面统计数据类入口
 * @author liuzhiping
 *
 */
@Controller
@RequestMapping(value="${adminPath}/statistics/")
public class StatisticsDataController extends BaseController{

	/**
	 * 运营统计页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sys/sysStatistics";
	}

	@RequestMapping(value = "testStatistics",method=RequestMethod.POST)
	@ResponseBody
	public Result testStatistics(HttpServletRequest request, HttpServletResponse response) {
		 //创建Option  
	    Option option = new Option();  
	    option.tooltip(Trigger.axis).legend("金额（元）");  
	    //横轴为值轴  
	    option.yAxis(new ValueAxis().boundaryGap(0d, 0.01));  
	    //创建类目轴  
	    CategoryAxis category = new CategoryAxis();  
	    //柱状数据  
	    Bar bar = new Bar("金额（元）");  
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    for(int i=0;i<10;i++){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("NAME","测试数据"+i);
	    	map.put("TOTAL",new Random().nextInt(1000));
	    	list.add(map);
	    }
	    //循环数据  
	    for (Map<String, Object> objectMap : list) {  
	        //设置类目  
	        category.data(objectMap.get("NAME"));  
	        //类目对应的柱状图  
	        bar.data(objectMap.get("TOTAL"));  
	    }  
	    //设置类目轴  
	    option.xAxis(category);  
	    //设置数据  
	    option.series(bar);  
		return ResultGenerator.genSuccessResult(option);
	}
	
	@RequestMapping(value = "testStatistics1",method=RequestMethod.POST)
	@ResponseBody
	public Result testStatistics1(HttpServletRequest request, HttpServletResponse response) {
		 //创建Option  
	    Option option = new Option();  
	    option.tooltip(Trigger.axis).legend("金额（元）");  
	    //饼图数据  
	    Pie pie = new Pie("金额（元）");  
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    for(int i=0;i<10;i++){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("NAME","测试数据"+i);
	    	map.put("TOTAL",new Random().nextInt(1000));
	    	list.add(map);
	    }
	    //循环数据  
	    for (Map<String, Object> objectMap : list) {  
	        //饼图数据  
	        pie.data(new PieData(objectMap.get("NAME").toString(), objectMap.get("TOTAL")));  
	    }  
	    //饼图的圆心和半径  
	    //pie.center(900,380).radius(100);  
	    
	    //设置数据  
	    option.series(pie);  
		return ResultGenerator.genSuccessResult(option);
	}
}
