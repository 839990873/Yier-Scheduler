package com.scu.ztz.yierschedulerutils.DAO;

import java.sql.Timestamp;
import java.util.List;

import com.scu.ztz.yierschedulerutils.DO.JobInfo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface JobInfoDao {
    @Select("select * from job_info where triggerable=1 and trigger_next_time <= #{maxNextTime} order by trigger_next_time")
    List<JobInfo> scheduleJobQuery(@Param("maxNextTime") Timestamp maxNextTime);

    @Update("Update job_info set trigger_last_time= #{triggerLastTime},trigger_next_time= #{triggerNextTime} where id = #{id}")
    public int scheduleUpdate(JobInfo jobInfo);

    @Insert("INSERT INTO job_info (job_description, email, schedule_type, schedule_plan, executor_handler, executor_param, executor_timeout, executor_fail_retry_count, glue_type, glue_source_code,glue_properties, triggerable, trigger_last_time, trigger_next_time, job_name, route_strategy) VALUES(#{jobDescription}, #{email}, #{scheduleType}, #{schedulePlan}, #{executorHandler}, #{executorParam}, #{executorTimeout}, #{executorFailRetryCount}, #{glueType},#{glueSource}, #{glueProerties}, #{triggerable}, #{triggerLastTime}, #{triggerNextTime}, #{jobName}, #{routeStrategy})")
    public int insert(JobInfo jobInfo);

    @Update("UPDATE job_info SET triggerable = 1, trigger_next_time = TIMESTAMPADD(second,2,current_timestamp()) WHERE id= #{jobId}")
    public int enableJobNow(int jobId);
    
    @Update("UPDATE job_info SET triggerable = 0 WHERE id= #{jobId}")
    public int disableJobNow(int jobId);

    @Select("select * from job_info where id = #{jobId}")
    JobInfo getJobInfo(@Param("jobId") int jobId);

    @Select("select glue_source_code from job_info where id = #{jobId}")
    String getGlueSource(@Param("jobId") int jobId);
}
