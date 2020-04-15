package com.lingoace.task.mapper.master;


import com.lingoace.task.entity.master.OpsUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 对接ops系统的表user_info
 */
@Mapper
public interface OpsUserInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OpsUserInfo record);

    int insertSelective(OpsUserInfo record);

    OpsUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpsUserInfo record);

    int updateByPrimaryKey(OpsUserInfo record);

    int batchInsert(@Param("list")List<OpsUserInfo> list);
}