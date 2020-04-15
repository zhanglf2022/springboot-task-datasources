package com.lingoace.task.task;

import com.lingoace.task.service.OpsUserInfoSynchronizationService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * [任务调度中心:同步ops库的user_info表]
 *
 * @author zhanglifeng
 * @date 2020-04-14
 */
@Component
public class OpsUserInfoSynchTask extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpsUserInfoSynchTask.class);

    @Autowired
    private OpsUserInfoSynchronizationService opsUserInfoSynchronizationService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LOGGER.info("------------------user_info表数据同步任务开始--------");
            opsUserInfoSynchronizationService.syncrOpsUserInfo();
            LOGGER.info("------------------user_info表数据同步任务结束--------");
        } catch (Exception e) {
            LOGGER.error("出错:{}", e);
        }
    }
}
