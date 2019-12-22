package com.thinkis.modules.telecom.web;

import com.thinkis.common.web.BaseController;
import com.thinkis.modules.telecom.service.HomeMeasureService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 游客访问的
 */
@Controller
@RequestMapping(value = "${frontPath}/telecom/homemeasure")
public class HomeMeasureController extends BaseController {

    @Autowired
    private HomeMeasureService homeMeasureService;

    /**
     * 获取最近七天的信号测试数据
     * @param response
     * @param model
     */
    @RequestMapping(value = "getWeekList")
    public void getTimeList(HttpServletResponse response, Model model){
        List<Map<String, Object>> maps = homeMeasureService.getWeekList();
        for(int i = 0; maps.size()>i ; i++){
            String s = maps.get(i).get("savetime").toString();
            maps.get(i).put("savetime",s);
        }
        try {
            PrintWriter out= response.getWriter();
            out.print(JSONArray.fromObject(maps).toString());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
