package com.scu.ztz.yierschedulerutils.DAO;
import com.scu.ztz.yierschedulerutils.DO.Executor;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExecutorDao {
    @Select("select * from executor_info")
    List<Executor> getAllExecutor();
}
