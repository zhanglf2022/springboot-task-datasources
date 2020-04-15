package com.lingoace.task.service.impl;

import com.lingoace.task.entity.master.OpsDataSynchronization;
import com.lingoace.task.entity.master.OpsUserInfo;
import com.lingoace.task.mapper.master.OpsDataSynchronizationMapper;
import com.lingoace.task.mapper.master.OpsUserInfoMapper;
import com.lingoace.task.mapper.slave.UserInfoMapper;
import com.lingoace.task.service.OpsUserInfoSynchronizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OpsUserInfoSynchronizationServiceImpl implements OpsUserInfoSynchronizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpsUserInfoSynchronizationServiceImpl.class);
    @Autowired
    private OpsUserInfoMapper opsUserInfoMapper;
    @Autowired
    private OpsDataSynchronizationMapper opsDataSynchronizationMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    /**
     * 限制一次查询的最大数据条数
     */
    private static final Integer LIMTI_COUNT = 100;

    @Override
    public void syncrOpsUserInfo() {
        OpsDataSynchronization opsDataSynchronization = opsDataSynchronizationMapper.selectByTableName("ops_data_synchronization");
        if (opsDataSynchronization != null && opsDataSynchronization.getStatus() == 0) {
            //说明同步任务已经停用
            return;
        }
        //sync_id的起始值定义
        int id = 0;
        int sync_result = 1;
        if (opsDataSynchronization != null) {
            id = opsDataSynchronization.getSyncId();
        }
        Calendar cal = Calendar.getInstance();
        Date startTime = cal.getTime();
        try {
            //说明这是第一次同步数据，数据量第一次一般比较大。
            int count = userInfoMapper.countUserInfo(id);
            //如果数据大于100条，则通过截数据来
            if (count > LIMTI_COUNT) {
                //获取循环次数
                int times = new BigDecimal(count).divide(new BigDecimal(100), 0, RoundingMode.UP).intValue();
                for (int i = 0; i < times; i++) {
                    List<OpsUserInfo> opsUserInfos = userInfoMapper.queryUserInfoListById(id);
                    //批量插入
                    opsUserInfoMapper.batchInsert(opsUserInfos);
                    //每次查询完，id变成最新的最大的id
                    id = opsUserInfos.get(opsUserInfos.size() - 1).getId();
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            sync_result = 0;
        }
        //本次同步结束后，将同步的信息更新入库。
        Date endTime = cal.getTime();
        if (opsDataSynchronization == null) {
            opsDataSynchronization = new OpsDataSynchronization();
            opsDataSynchronization.setTimes(1);
            opsDataSynchronization.setStartTime(startTime);
            opsDataSynchronization.setSyncId(id);
            opsDataSynchronization.setSyncResult(sync_result);
            opsDataSynchronization.setTbName("ops_data_synchronization");
            opsDataSynchronization.setEndTime(endTime);
            opsDataSynchronization.setStatus(1);
            opsDataSynchronizationMapper.insert(opsDataSynchronization);
        } else {
            opsDataSynchronization.setTimes(opsDataSynchronization.getTimes() + 1);
            opsDataSynchronization.setStartTime(startTime);
            opsDataSynchronization.setSyncId(id);
            opsDataSynchronization.setSyncResult(sync_result);
            opsDataSynchronization.setTbName("ops_data_synchronization");
            opsDataSynchronization.setEndTime(endTime);
            opsDataSynchronizationMapper.updateByPrimaryKeySelective(opsDataSynchronization);
        }
    }


}
