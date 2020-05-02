package com.fyp.eholeservice.eholeservice.Component;

import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Service.EholeService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class ChangeEholeStatusJob extends QuartzJobBean {

    @Autowired
    EholeService eholeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {

        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Long id = jobDataMap.getLongValue("eholeId");
        String status = jobDataMap.getString("status");

        try {
            boolean status1 = eholeService.updateEholeStatus(id, EholeStatusType.findByLabel(status));
            if (status1) {
                System.out.println("EHOLE " + id + "UPDATED");
            } else {
                System.out.println("FAILED " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
