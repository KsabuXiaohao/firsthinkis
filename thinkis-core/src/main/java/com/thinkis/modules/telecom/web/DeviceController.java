package com.thinkis.modules.telecom.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.thinkis.common.beanvalidator.BeanValidators;
import com.thinkis.common.config.Global;
import com.thinkis.common.db.DynamicDataSource;
import com.thinkis.common.oauth.util.HttpKit;
import com.thinkis.common.persistence.Page;
import com.thinkis.common.utils.*;
import com.thinkis.common.utils.excel.ExportExcel;
import com.thinkis.common.utils.excel.ImportExcel;
import com.thinkis.common.utils.http.Constant;
import com.thinkis.common.utils.http.HttpsUtil;
import com.thinkis.common.utils.http.StreamClosedHttpResponse;
import com.thinkis.common.utils.http.StringUtil;
import com.thinkis.common.web.BaseController;
import com.thinkis.common.web.Result;
import com.thinkis.common.web.ResultGenerator;
import com.thinkis.modules.sys.entity.Office;
import com.thinkis.modules.sys.entity.User;
import com.thinkis.modules.sys.service.OfficeService;
import com.thinkis.modules.sys.service.SystemService;
import com.thinkis.modules.sys.utils.DictUtils;
import com.thinkis.modules.sys.utils.UserUtils;
import com.thinkis.modules.telecom.entity.Device;
import com.thinkis.modules.telecom.entity.IotEvent;
import com.thinkis.modules.telecom.entity.IotToken;
import com.thinkis.modules.telecom.entity.importDeviceHistory;
import com.thinkis.modules.telecom.service.DeviceService;
import com.thinkis.modules.telecom.service.IotEventService;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value = "${adminPath}/telecom/device")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private IotEventService iotEventServicel;

    private IotToken iotToken = new IotToken();

    private String token = "RBSB84U+z9i69Po3jaWfUV9m612EuMtH";

    private Logger logger = Logger.getLogger(this.getClass());
    /**
     * 设备入口
     * @return
     */
    @RequestMapping(value = "From")
    public String From(Model model) {
        return "modules/tph/device";
    }


    @RequestMapping(value = "list")
    @ResponseBody
    public Result list(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<Device> page = deviceService.findPage(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "listByCompanyId")
    @ResponseBody
    public Result listByCompanyId(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {

            Page<Device> page = deviceService.getDeviceByCompanyId(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 导入用户数据
     * @param file
     * @param redirectAttributes
     * @return
     */
//    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(Device d,MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            User user = new User();
            if(StringUtils.isEmpty(d.getCompanyid())){
                user = UserUtils.getUser();
            }else{
                user.setCompany(officeService.get(d.getCompanyid()));
            }
            List<Device> list = ei.getDataList(Device.class);
            for (Device device : list){
                try{
                    if(device.getMeid().indexOf("E")>0){
                        BigDecimal bd1 = new BigDecimal(device.getMeid());
                        device.setMeid(bd1.toPlainString());
                    }
                    int i = deviceService.getMeid(device.getMeid());
                    device.setCompany(user.getCompany().getName());
                    device.setCompanyid(user.getCompany().getId());
                    if(i>0){
                          deviceService.setCompanyid(device);
                          failureNum++;
                    }else{
                          deviceService.saveCompanyid(device);
                          successNum++;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (failureNum>0){
                failureMsg.insert(0, "，设备更新 "+failureNum+" 条");
            }
            return  ResultGenerator.genSuccessResult("已成功导入 "+successNum+" 条设备"+failureMsg);
//            addMessage(redirectAttributes, "已成功导入 "+successNum+" 条设备"+failureMsg);
        } catch (Exception e) {
            return  ResultGenerator.genSuccessResult("导入设备失败！失败信息："+e.getMessage());
        }
    }

    /**
     * 下载导入用户数据模板
     * @param response
     * @param redirectAttributes
     * @return
     */
//    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "设备数据导入模板.xlsx";
            List<Device> list = Lists.newArrayList();

            list.add(deviceService.getDeviceDemo());
            new ExportExcel("设备数据", Device.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            logger.info("导入模板下载失败！失败信息："+e.getMessage());
            addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/telecom/device/list?repage";
    }

    @RequestMapping(value = "saveform")
    public String saveform(Device device, Model model) {
        try {
            if (StringUtils.isNotEmpty(device.getId())) {
                device = deviceService.get(device.getId());
                model.addAttribute("device", device);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        model.addAttribute("offices", officeService.findAll());
        return "modules/tph/deviceForm";
    }

    /**
     * 设备导入
     * @param device
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public Result save(Device device, Model model, RedirectAttributes redirectAttributes) {
        HttpsUtil  httpsUtil = new HttpsUtil();
        Office office = new Office();
        try{
        httpsUtil.initSSLConfigForTwoWay();
        this.getToken(httpsUtil,0);
        if(StringUtils.isEmpty(device.getCompanyid())){
            device.setCompanyid(UserUtils.getUser().getCompany().getId());
        }
        office = officeService.get(device.getCompanyid());
        device.setCompany(office.getName());

            if(StringUtils.isEmpty(device.getId())){
                Map<String,String> Header = new HashMap<>();
                Header.put("app_key",Constant.APPID);
                Header.put("Authorization",iotToken.getAccesstoken());
                Header.put("Content-Type","application/json");
                Map<String,Object> map = new HashMap<>();
                map.put("nodeId",device.getImei());
                map.put("appId",Constant.APPID);
                String jsonString = JSON.toJSONString(map);
                StreamClosedHttpResponse responseLogin = httpsUtil.doPostJsonGetStatusLine(""+Constant.BASE_URL+"iocm/app/reg/v1.1.0/deviceCredentials", Header,jsonString);
                logger.debug(responseLogin.getContent());
                JSONObject jsonObject = JSONObject.parseObject(responseLogin.getContent());
                Map<String,String> parms = (Map)jsonObject;
                device.setDeviceid(parms.get("deviceId"));
                if(StringUtils.isNotEmpty(device.getDeviceid())){
                    Map<String,Object> map2 = new HashMap<>();
                    map2.put("name","testthree"+System.currentTimeMillis());
                    map2.put("manufacturerId","JunyanTest");
                    map2.put("manufacturerName","shanghaijunyanTest");
                    map2.put("deviceType","WaterMeter");
                    map2.put("model","WPTA");
                    map2.put("location","shanghai");
                    map2.put("protocolType","CoAP");
                    String jsonString2 = JSON.toJSONString(map2);
                    HttpResponse responseLogin2 = httpsUtil.doPutJson(""+Constant.BASE_URL+"iocm/app/dm/v1.4.0/devices/"+device.getDeviceid()+"?appId="+Constant.APPID+"", Header,jsonString2);
                    logger.info(String.valueOf(responseLogin2.getStatusLine().getStatusCode()));
                    if(responseLogin2.getStatusLine().getStatusCode()==204){
                        if(StringUtils.isNotEmpty(device.getSendaddress())&&StringUtils.isNotEmpty(device.getSendtype())){
                            Map<String,Object> jedisJson = new HashMap<>();
                            String[] parts = device.getSendaddress().split("\\:", 2);
                            jedisJson.put("ip",parts[0]);
                            jedisJson.put("port",parts[1]);
                            jedisJson.put("protocol",device.getSendtype());
                            JedisUtils.set(device.getDeviceid(),JSON.toJSONString(jedisJson),0);
                        }
                        Map<String,Object> httpjson = new HashMap<>();
                        httpjson.put("deviceId",device.getDeviceid());
                        HttpClientUtil.doPostToken("http://47.99.183.68:8880/api/iot/newDevice",JSON.toJSONString(httpjson),token);
                        deviceService.saveCompanyid(device);
                    }else if(responseLogin.getStatusLine().getStatusCode()==403) {
                        this.getToken(httpsUtil, 403);
                        this.save(device, model, redirectAttributes);
                    }
                }else if(responseLogin.getStatusLine().getStatusCode()==403) {
                    this.getToken(httpsUtil, 403);
                    this.save(device, model, redirectAttributes);
                }else{
                    return ResultGenerator.genSuccessResult("数据添加失败!请联系管理员。");
                }
            } else{
                if(StringUtils.isNotEmpty(device.getSendaddress())&&StringUtils.isNotEmpty(device.getSendtype())){
                    Map<String,Object> jedisJson = new HashMap<>();
                    String[] parts = device.getSendaddress().split("\\:", 2);
                    jedisJson.put("ip",parts[0]);
                    jedisJson.put("port",parts[1]);
                    jedisJson.put("protocol",device.getSendtype());
                    JedisUtils.set(device.getDeviceid(),JSON.toJSONString(jedisJson),0);
                }
                deviceService.setCompanyid(device);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultGenerator.genSuccessResult("数据添加失败！失败信息："+e.getMessage());
        }
        return ResultGenerator.genSuccessResult("保存设备到" + office.getName() + "成功");
    }

    /**
     * 导入入口
     * @param model
     * @return
     */
    @RequestMapping(value = "importform")
    public String importform(Model model) {
        model.addAttribute("offices", officeService.findAll());
        return "modules/tph/deviceImort";
    }


    @RequestMapping(value = "delete")
    public String delete(Device device,RedirectAttributes redirectAttributes,Model model) {
        HttpsUtil  httpsUtil = new HttpsUtil();
        try {
            httpsUtil.initSSLConfigForTwoWay();
            this.getToken(httpsUtil,0);
            Map<String,String> Header = new HashMap<>();
            Header.put("app_key",Constant.APPID);
            System.out.println(Constant.APPID);
            System.out.println(iotToken.getAccesstoken());
            Header.put("Authorization",iotToken.getAccesstoken());
            Header.put("Content-Type","application/json");
            Map<String,String> map = new HashMap<>();
            map.put("appId",Constant.APPID);
            HttpResponse responseLogin = httpsUtil.doDeleteWithParas(""+Constant.BASE_URL+"/iocm/app/dm/v1.4.0/devices/"+device.getDeviceid()+"",map,Header);
            logger.debug(String.valueOf(responseLogin.getStatusLine().getStatusCode()));
            if(responseLogin.getStatusLine().getStatusCode()==204 || responseLogin.getStatusLine().getStatusCode()==404) {
                if(StringUtils.isNotEmpty(UserUtils.getUser().getCompany().getSendaddress())){
                    JedisUtils.del(device.getDeviceid());
                }
                Map<String,Object> httpjson = new HashMap<>();
                httpjson.put("deviceId",device.getDeviceid());
                HttpClientUtil.doPostToken("http://47.99.183.68:8880/api/iot/removeDevice",JSON.toJSONString(httpjson),token);
                deviceService.delete(device);
            }else if (responseLogin.getStatusLine().getStatusCode()==403){
                this.getToken(httpsUtil,403);
                this.delete(device, redirectAttributes, model);
            }
        }catch (Exception e){
            e.printStackTrace();
            addMessage(redirectAttributes, "删除失败！失败信息："+e.getMessage());
        }
        addMessage(redirectAttributes, "删除成功！");
        return "redirect:" + adminPath + "/telecom/device/list?repage";
    }

    /**
     * 获取查询日期范围数据or最近数据
     * @param response
     * @param sdate
     * @param edate
     */
    @RequestMapping(value = "getTimeList")
    public void getTimeList(String sdate,String edate, HttpServletResponse response){
        try {
            List<Map<String, Object>> maps = new ArrayList<>();
            if(StringUtils.isEmpty(sdate)){
                maps = deviceService.getWeekList();
            }else{
                maps = deviceService.getTimeList(sdate,edate);
            }
            maps = hmap(maps);
            for(int i = 0; maps.size()>i ; i++){
                String s = maps.get(i).get("CREATED_STAMP").toString();
                maps.get(i).put("CREATED_STAMP",s);
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
     * 获取最近测试数据
     * @param response
     * @param model
     */
    @RequestMapping(value = "getWeekList")
    public void getWeekList(HttpServletResponse response, Model model){
        List<Map<String, Object>> maps = deviceService.getWeekList();
        for(int i = 0; maps.size()>i ; i++){
            String s = maps.get(i).get("CREATED_STAMP").toString();
            maps.get(i).put("CREATED_STAMP",s);
        }
        maps = hmap(maps);
        try {
            PrintWriter out= response.getWriter();
            out.print(JSONArray.fromObject(maps).toString());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取查询日期范围数据or最近数据
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
                maps = deviceService.getWeekListById(user.getCompany().getId());
            }else{
                maps = deviceService.getTimeListById(sdate,edate,user.getCompany().getId());
            }
            maps = hmap(maps);
            for(int i = 0; maps.size()>i ; i++){
                String s = maps.get(i).get("CREATED_STAMP").toString();
                maps.get(i).put("CREATED_STAMP",s);
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
     * 获取设备高低压报警
     * @param iotEvent
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "getAlarmList")
    @ResponseBody
    public Result getAlarmList(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<Device> page = deviceService.getAlarmList(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "getkhAlarmList")
    @ResponseBody
    public Result getkhAlarmList(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            device.setCompanyid(UserUtils.getUser().getCompany().getId());
            Page<Device> page = deviceService.getkhAlarmList(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping(value = "setSmsg")
    public void setSmsg(HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {
        PrintWriter out = null;
        try {
            out= response.getWriter();
            logger.debug("短信发送成功");
//            addMessage(redirectAttributes, "发送短信成功！");
            Map<String,Object> map = new HashMap<>();
            out.print("0");
//            SmsUtil.sendSms("17612163089","{\"name\":\"群众2\", \"phone_num\":\"02155444022\", \"help\":\"咨询\", \"user_question\":\"喂你好，我想问一下，嗯关于那个养老...\"}","SMS_162547280");
        }catch (Exception e){
            out.print("1");
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }
//        return "modules/tph/deviceForm";
    }

    @RequestMapping(value = "setalert")
    public void setalert(HttpServletRequest request,HttpServletResponse response,Model model,RedirectAttributes redirectAttributes) {

    }

    /**
     * 设备历史入口
     * @param model
     * @return
     */
    @RequestMapping(value = "findListHistoryFrom")
    public String findListHistoryFrom(Model model) {
        return "modules/tph/deviceHistory";
    }

    /**
     * 表格历史
     * @param device
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findListHistory")
    @ResponseBody
    public Result findListHistory(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<Device> page = deviceService.findListHistory(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 客户表格历史
     * @param device
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "findListkhHistory")
    @ResponseBody
    public Result findListkhHistory(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            device.setCompanyid(UserUtils.getUser().getCompany().getId());
            Page<Device> page = deviceService.findListkhHistory(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    @RequestMapping(value = "imeiHistory")
    public String imeiHistory(Device device, Model model) {
        model.addAttribute("device", device);
        return "modules/tph/imeiHistory";
    }

    @RequestMapping(value = "findImeihHistory")
    @ResponseBody
    public Result findImeihHistory(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<Device> page = deviceService.findImeihHistory(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "chartImeihHistory")
    public void chartImeihHistory(String imei, HttpServletResponse response) {
        try {
            Device device = new Device();
            device.setImei(imei);
            List<Map<String,Object>> devices = deviceService.chartImeihHistory(device);
            PrintWriter out= response.getWriter();
            System.out.print(JSONArray.fromObject(devices).toString());
            out.print(JSONArray.fromObject(devices).toString());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
            logger.info("chartImeihHistory-----------"+e.getMessage());
        }
    }

    /**
     * 表格历史
     * @param device
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "chartHistory")
    @ResponseBody
    public Result chartHistory(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Page<Device> page = deviceService.findListHistory(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 客户表格历史
     * @param device
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "chartkhHistory")
    @ResponseBody
    public Result chartkhHistory(Device device, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            device.setCompanyid(UserUtils.getUser().getCompany().getId());
            Page<Device> page = deviceService.chartkhHistory(new Page<Device>(request, response), device);
            return ResultGenerator.genSuccessResult(page.getCount(), page.getList());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载用户设备上报数据
     * @param response
     * @param redirectAttributes
     * @return
     */
//    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/imeiHistory")
    public String importImeiHistory(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String type =request.getParameter("type");
            String fileName = "设备数据上报信息.xlsx";
            Device device = new Device();
            if("1".equals(type)){
                device.setCompanyid(UserUtils.getUser().getCompany().getId());
            }
            new ExportExcel("设备数据", Device.class, 2).setDataList(deviceService.importImeiHistory(device)).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/telecom/device/list?repage";
    }

    /**
     * 下载设备创建历史
     * @param response
     * @param redirectAttributes
     * @return
     */
//    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/importDeviceHistory")
    public String importDeviceHistory(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String type =request.getParameter("type");
            String fileName = "设备创建历史信息.xlsx";
            Device device = new Device();
            if("1".equals(type)){
                device.setCompanyid(UserUtils.getUser().getCompany().getId());
            }
            new ExportExcel("设备创建历史数据", importDeviceHistory.class, 2).setDataList(deviceService.importDeviceHistory(device)).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/telecom/device/list?repage";
    }

    /**
     * 下载设备创建历史
     * @param response
     * @param redirectAttributes
     * @return
     */
//    @RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/importkhDeviceHistory")
    public String importkhDeviceHistory(HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String type =request.getParameter("type");
            String fileName = "设备创建历史信息.xlsx";
            Device device = new Device();
            device.setCompanyid(UserUtils.getUser().getCompany().getId());
            new ExportExcel("设备创建历史数据", importDeviceHistory.class, 2).setDataList(deviceService.importDevicekhHistory(device)).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
        }
        return "redirect:" + adminPath + "/telecom/device/list?repage";
    }

    /**
     * 因为需要切换数据库，所以吧改部分脱离出来，作为引用执行
     * @param maps
     * @return
     */
    private List<Map<String, Object>> hmap(List<Map<String, Object>> maps){
//        DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATASOURCE_BEI);
        try {
            if (maps != null) {
                for (int i = 0; maps.size() > i; i++) {
                    String deviceid = maps.get(i).get("deviceid").toString();
                    if (StringUtils.isNotEmpty(deviceid)) {
                        Map<String, Object> map = iotEventServicel.getDeviceid(deviceid);
                        if (!map.isEmpty()) {
                            maps.get(i).putAll(map);
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
//            DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATASOURCE_ZHU);
        }
        return maps;
    }


    /**
     * 获取鉴权
     * @param httpsUtil
     */
    public void getToken(HttpsUtil httpsUtil, Integer code){
        Map<String,String> objectMap = new HashMap<>();
        objectMap.put("appId",Constant.APPID);
        objectMap.put("secret",Constant.SECRET);
        try {
            Map<String,Object> jedisMap = new HashMap<>();
            if(StringUtils.isEmpty(iotToken.getAccesstoken())) {
                jedisMap = JedisUtils.getObjectMap("iotToken");
                if (null==jedisMap) {
                    StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine("" + Constant.BASE_URL + "iocm/app/sec/v1.1.0/login", objectMap);
                    JSONObject jsonObject = JSONObject.parseObject(responseLogin.getContent());
                    Map<String, Object> parms = (Map) jsonObject;
                    iotToken.setScope(parms.get("scope").toString());
                    iotToken.setTokentype(parms.get("tokenType").toString());
                    iotToken.setExpiresin(parms.get("expiresIn").toString());
                    iotToken.setAccesstoken(parms.get("accessToken").toString());
                    iotToken.setRefreshtoken(parms.get("refreshToken").toString());
                    JedisUtils.setObjectMap("iotToken", parms, 0);
                }
                if(StringUtils.isEmpty(iotToken.getAccesstoken())){
                    iotToken.setScope(jedisMap.get("scope").toString());
                    iotToken.setTokentype(jedisMap.get("tokenType").toString());
                    iotToken.setExpiresin(jedisMap.get("expiresIn").toString());
                    iotToken.setAccesstoken(jedisMap.get("accessToken").toString());
                    iotToken.setRefreshtoken(jedisMap.get("refreshToken").toString());
                }
            }
            if (code == 403) {
                StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine("" + Constant.BASE_URL + "iocm/app/sec/v1.1.0/login", objectMap);
                JSONObject jsonObject = JSONObject.parseObject(responseLogin.getContent());
                Map<String, Object> parms = (Map) jsonObject;
                iotToken.setScope(parms.get("scope").toString());
                iotToken.setTokentype(parms.get("tokenType").toString());
                iotToken.setExpiresin(parms.get("expiresIn").toString());
                iotToken.setAccesstoken(parms.get("accessToken").toString());
                iotToken.setRefreshtoken(parms.get("refreshToken").toString());
                JedisUtils.setObjectMap("iotToken", parms, 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
