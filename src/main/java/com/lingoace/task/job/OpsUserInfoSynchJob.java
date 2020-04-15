package com.lingoace.task.job;

import com.lingoace.task.task.OpsUserInfoSynchTask;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

/**
 * [定时任务触发器]
 *
 * @author zhanglifeng
 * @date 2020-04-14
 */
@Component
public class OpsUserInfoSynchJob {

    @Bean(name = "opsUserInfoSynchTrigger")
    public CronTriggerFactoryBean jobLingoAceTestTrigger(@Qualifier("jobOpsUserInfoSynchDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetail);
        cronTriggerFactoryBean.setCronExpression("0 */2 * * * ?");
        return cronTriggerFactoryBean;
    }

    @Bean(name = "jobOpsUserInfoSynchDetail")
    public JobDetailFactoryBean jobOpsUserInfoSynchDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(OpsUserInfoSynchTask.class);
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setRequestsRecovery(true);
        return jobDetailFactoryBean;
    }

}
