package com.lingoace.task.mapper.slave;


import com.lingoace.task.entity.master.OpsUserInfo;
import com.lingoace.task.entity.slave.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    //每次查询最多100条id>#｛id｝的数据
    List<OpsUserInfo> queryUserInfoListById(Integer id);

    //查询id>#{id}的数据总条数
    int countUserInfo(Integer id);
}