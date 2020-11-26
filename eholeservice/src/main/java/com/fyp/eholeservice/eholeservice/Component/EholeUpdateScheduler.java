package com.fyp.eholeservice.eholeservice.Component;

import com.fyp.eholeservice.eholeservice.Entity.EholeEntity;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Service.EholeService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.quartz.DateBuilder.futureDate;

@Component
public class EholeUpdateScheduler {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private EholeService eholeService;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @Autowired
    RestTemplate restTemplate;

    private List<EholeEntity> eholeEntityList = new ArrayList<>();


    @Scheduled(fixedRate = 30000)
    public void fetchNewEholes() {
        List<EholeEntity> allEholesByStatus = eholeService.getAllEholesByStatus(EholeStatusType.ACTIVE);
        if (eholeEntityList.size() == 0) {
            startEholeUpdateScheduler(allEholesByStatus);
        } else {
            List<EholeEntity> temp = new ArrayList<>();
            for (EholeEntity entity :
                    allEholesByStatus) {
                for (EholeEntity entity1 :
                        eholeEntityList) {
                    if (entity1.equals(entity)) {
                        temp.add(entity);
                    }
                }
            }
            allEholesByStatus.removeAll(temp);
            if (allEholesByStatus.size() == 0) {
                System.out.println("NO NEW EHOLE FOUND");
            } else {
                startEholeUpdateScheduler(allEholesByStatus);
            }
        }
        if (allEholesByStatus.size() != 0) {
            eholeEntityList.addAll(allEholesByStatus);
        }
    }

    private void startEholeUpdateScheduler(List<EholeEntity> allEholesByStatus) {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        long until;
        JobDetail jobDetail;
        Trigger trigger;

//        List<EholeEntity> allEholesByStatus = eholeService.getAllEholesByStatus(EholeStatusType.ACTIVE);
        if (allEholesByStatus.size() == 0) {
            System.out.println("NO ACTIVE POOLS");
            return;
        }
        for (EholeEntity eholeEntity :
                allEholesByStatus) {
            if (now.isBefore(eholeEntity.getCompletionDate()) || now.isEqual(eholeEntity.getCompletionDate())) {
                until = now.until(eholeEntity.getCompletionDate(), ChronoUnit.MINUTES);
                jobDetail = buildJobDetail(eholeEntity.getId(), EholeStatusType.TRADING);
            } else {
                until = 0;
                double requiredAmount = eholeEntity.getTotalAmount() * 0.75;
                if (requiredAmount > eholeEntity.getCompletedAmount()) {
                    jobDetail = buildJobDetail(eholeEntity.getId(), EholeStatusType.CANCELED);
                    System.out.println("E HOLE CANCELLED");
                } else {
                    jobDetail = buildJobDetail(eholeEntity.getId(), EholeStatusType.TRADING);
                }
            }
            trigger = buildJobTrigger(jobDetail, Math.toIntExact(until));
            try {
                Date date = scheduler.scheduleJob(jobDetail, trigger);
                System.out.println("EHOLE " + eholeEntity.getId() + "STARTS IN " + date.toGMTString());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }
    }

    private JobDetail buildJobDetail(Long eholeId, EholeStatusType eholeStatusType) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("eholeId", eholeId);
        jobDataMap.put("status", eholeStatusType.getLabel());

        return JobBuilder.newJob(ChangeEholeStatusJob.class)
                .withIdentity(UUID.randomUUID().toString(), "ehole-jobs")
                .withDescription("update ehole status")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, int delay) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "ehole-triggers")
                .withDescription("update ehole status")
                .startAt(futureDate(delay, DateBuilder.IntervalUnit.MINUTE))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

//    @Scheduled(fixedRate = 30000)
//    public void endEhole() {
//        String url = urlPropertyBundle.getTradingUrl() + "/ehole/co/";
//
//        List<EholeEntity> allEholesByStatus = eholeService.getAllEholesByStatus(EholeStatusType.TRADING);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        HttpEntity httpEntity = new HttpEntity(headers);
//
//        for (EholeEntity entity :
//                allEholesByStatus) {
//            ResponseEntity<Double> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Double.class);
//        }
//    }

}
