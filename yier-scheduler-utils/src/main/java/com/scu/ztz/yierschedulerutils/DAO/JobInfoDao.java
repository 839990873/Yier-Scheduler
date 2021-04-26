package com.scu.ztz.yierschedulerutils.DAO;

import java.util.List;

import com.scu.ztz.yierschedulerutils.DO.JobInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface JobInfoDao {
    @Select("select * from job_info where triggerable=1 and trigger_next_time < #{maxNextTime} order by trigger_next_time")
    List<JobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime);

    @Update("Update job_info set trigger_last_time= #{trigger_last_time},trigger_next_time= #{triggerNextTime}")
    public int scheduleUpdate(JobInfo jobInfo);
}
