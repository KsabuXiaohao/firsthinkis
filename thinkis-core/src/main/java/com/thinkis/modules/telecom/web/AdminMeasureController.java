package com.thinkis.modules.telecom.web;


import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.DateUtils;
import com.thinkis.common.utils.excel.ExportExcel;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.utils.UserUtils;
import com.thinkis.modules.telecom.entity.HomeMeasure;
import org.apache.commons.lang.StringUtils;
import com.thinkis.common.web.BaseController;
import com.thinkis.modules.telecom.service.HomeMeasureService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/telecom/homemeasure")
public class AdminMeasureController extends BaseController {

    @Autowired
    private HomeMeasureService homeMeasureService;

    @RequestMapping(value = "tMap")
    public String from() {
        return "modules/tph/map";
    }

    /**
     * 获取查询日期范围数据or最近七天数据
     * @param response
     * @param sdate
     * @param edate
     */
    @RequestMapping(value = "getTimeList")
    public void getTimeList(String sdate,String edate, HttpServletResponse response){
        try {
            List<Map<String, Object>> maps = new ArrayList<>();
            if(StringUtils.isEmpty(sdate)){
                maps = homeMeasureService.getWeekList();
            }else{
                maps = homeMeasureService.getTimeList(sdate,edate);
            }
            for(int i = 0; maps.size()>i ; i++){
                String s = maps.get(i).get("savetime").toString();
                maps.get(i).put("savetime",s);
            }
                PrintWriter out= response.getWriter();
                out.print(JSONArray.fromObject(maps).toString());
                out.flush();
                out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 统计入口
     * @return
     */
    @RequestMapping(value = "IotStatisticsFrom")
    public String IotStatisticsFrom() {
        return "modules/tph/IotStatistics";
    }

    /**
     * 信号统计分页查询
     * @param homeMeasure
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getList",method=RequestMethod.GET)
    @ResponseBody
    public Result getList(HomeMeasure homeMeasure, HttpServletRequest request, HttpServletResponse response) {
        try {
            Page<HomeMeasure> page = homeMeasureService.findPage(new Page<HomeMeasure>(request, response), homeMeasure);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 导出Excel
     * @param homeMeasure
     * @param response
     * @param model
     * @return
     */
//    @RequiresPermissions("modules:ywphone:view")
    @RequestMapping(value = "download")
    public @ResponseBody
    String download(HomeMeasure homeMeasure, HttpServletResponse response, Model model) {

        List<HomeMeasure> homeMeasureList = homeMeasureService.getHomeMeasure(homeMeasure);
        try {
            String fileName = "测试数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            new ExportExcel("测试数据", HomeMeasure.class).setDataList(homeMeasureList).write(response, fileName).dispose();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取查询日期范围数据or最近七天个人数据
     * @param response
     * @param sdate
     * @param edate
     */
    @RequestMapping(value = "getTimeListById")
    public void getTimeListById(String sdate,String edate, HttpServletResponse response){
        try {
            User user = UserUtils.getUser();
            List<Map<String, Object>> maps = new ArrayList<>();
            if(StringUtils.isEmpty(sdate)){
                maps = homeMeasureService.getWeekListById(user.getId());
            }else{
                maps = homeMeasureService.getTimeListById(sdate,edate,user.getId());
            }
            for(int i = 0; maps.size()>i ; i++){
                String s = maps.get(i).get("savetime").toString();
                maps.get(i).put("savetime",s);
            }
            PrintWriter out= response.getWriter();
            out.print(JSONArray.fromObject(maps).toString());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 统计入口
     * @return
     */
    @RequestMapping(value = "IotUserStatisticsFrom")
    public String IotUserStatisticsFrom() {
        return "modules/tph/IotUserStatistics";
    }
    /**
     * 统计分页查询
     * @param homeMeasure
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getListById",method=RequestMethod.GET)
    @ResponseBody
    public Result getListById(HomeMeasure homeMeasure, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = UserUtils.getUser();
            homeMeasure.setId(user.getId());
            Page<HomeMeasure> page = homeMeasureService.findPageById(new Page<HomeMeasure>(request, response), homeMeasure);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 次数入口
     * @return
     */
    @RequestMapping(value = "numberStatisticsFrom")
    public String numberStatisticsFrom() {
        return "modules/tph/numberStatistics";
    }

    /**
     * 次数入口
     * @return
     */
    @RequestMapping(value = "numberUserStatisticsFrom")
    public String numberUserStatisticsFrom() {
        return "modules/tph/numberUserStatistics";
    }


    /**
     * 次数统计分页查询
     * @param homeMeasure
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getNumberList",method=RequestMethod.GET)
    @ResponseBody
    public Result getNumberList(HomeMeasure homeMeasure, HttpServletRequest request, HttpServletResponse response) {
        try {
            Page<HomeMeasure> page = homeMeasureService.getNumberList(new Page<HomeMeasure>(request, response), homeMeasure);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 次数统计分页查询
     * @param homeMeasure
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getNumberListById",method=RequestMethod.GET)
    @ResponseBody
    public Result getNumberListById(HomeMeasure homeMeasure, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = UserUtils.getUser();
            homeMeasure.setId(user.getId());
            Page<HomeMeasure> page = homeMeasureService.getNumberListById(new Page<HomeMeasure>(request, response), homeMeasure);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
