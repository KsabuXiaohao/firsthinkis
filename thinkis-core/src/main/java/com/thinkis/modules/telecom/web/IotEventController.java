package com.thinkis.modules.telecom.web;

import com.thinkis.common.db.DynamicDataSource;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.telecom.entity.IotEvent;
import com.thinkis.modules.telecom.service.IotEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/telecom/iotEvent")
public class IotEventController extends BaseController {

    @Autowired
    IotEventService iotEventService;


    @RequestMapping(value = "iotEventForm")
    public String from() {
        return "modules/tph/iotEvent";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public Result list(HttpServletRequest request, HttpServletResponse response) {
        IotEvent iotEvent = new IotEvent();
        try {
//            DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATASOURCE_BEI);
            Page<IotEvent> page = iotEventService.getPageList(new Page<IotEvent>(request, response), iotEvent);
//            DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATASOURCE_ZHU);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
