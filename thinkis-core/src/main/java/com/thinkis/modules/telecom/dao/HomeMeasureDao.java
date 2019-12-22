package com.thinkis.modules.telecom.dao;

import com.thinkis.common.persistence.CrudDao;
import com.thinkis.common.persistence.annotation.MyBatisDao;
import com.thinkis.modules.telecom.entity.HomeMeasure;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface HomeMeasureDao extends CrudDao<HomeMeasure> {

    @Select("SELECT * FROM home_measure where DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(savetime) ")
    List<Map<String,Object>> getWeekList();
    @Select("select  * from home_measure where savetime >= #{sdate} and savetime <= #{edate} ")
    List<Map<String,Object>> getTimeList(@Param("sdate") String sdate,@Param("edate") String edate);
    @Select("select c.* from sys_user a left join device b on a.id = b.companyid left join home_measure c on b.meid = c.meid " +
            " where a.id = #{id} and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(c.savetime)")
    List<Map<String,Object>> getWeekListById(@Param("id") String id);
    @Select("select c.* from sys_office a left join device b on a.id = b.companyid left join home_measure c on b.meid = c.meid " +
            " where c.savetime >= #{sdate} and c.savetime <= #{edate} and a.id = #{id}")
    List<Map<String,Object>> getTimeListById(@Param("sdate") String sdate,@Param("edate") String edate,@Param("id") String id);






    List<HomeMeasure> getHomeMeasure(HomeMeasure entity);
    List<HomeMeasure> findListById(HomeMeasure entity);
    List<HomeMeasure> getNumberList(HomeMeasure entity);
    List<HomeMeasure> getNumberListById(HomeMeasure entity);
}
