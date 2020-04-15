package com.lingoace.task.mapper.master;


import com.lingoace.task.entity.master.OpsDataSynchronization;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpsDataSynchronizationMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OpsDataSynchronization record);

    int insertSelective(OpsDataSynchronization record);

    OpsDataSynchronization selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpsDataSynchronization record);

    int updateByPrimaryKey(OpsDataSynchronization record);

    OpsDataSynchronization selectByTableName(String tableName);
}