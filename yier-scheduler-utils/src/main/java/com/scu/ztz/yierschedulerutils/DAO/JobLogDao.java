package com.scu.ztz.yierschedulerutils.DAO;

import java.sql.Timestamp;
import java.util.List;

import com.scu.ztz.yierschedulerutils.DO.JobInfo;
import com.scu.ztz.yierschedulerutils.DO.JobLog;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface JobLogDao {
    @Insert("INSERT INTO job_log (job_id,executor_address, trigger_time) VALUES (#{jobId},#{executorAddress},#{triggerTime});")
    @Options(useGeneratedKeys = true ,keyProperty = "id", keyColumn = "id")
    public int newTrigger(JobLog jobLog);

    @Update("Update job_log set trigger_msg = #{msg}, trigger_status=#{status} where id=#{logId}")
    public int setTriggerStatus(@Param("logId") int logId, @Param("status") String status,@Param("msg") String msg);

    @Update("Update job_log set handle_time = #{time} where id=#{logId}")
    public int newHandle(@Param("logId") int logId, @Param("time")Timestamp time);

    @Update("Update job_log set handle_msg = #{msg}, handle_status=#{status} where id=#{logId}")
    public int setHandleStatus(@Param("logId") int logId, @Param("status") String status, @Param("msg") String msg);
}
