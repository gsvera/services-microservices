package com.esthetic.servicesmicroservices.cron;

import com.esthetic.servicesmicroservices.services.ScheduleServiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component

public class ServicesTask {
    @Autowired
    private ScheduleServiceServices scheduleServiceServices;
    //3 horas × 60 min × 60 seg × 1000 ms = 43,200,000 ms'
    @Scheduled(fixedDelay = 10800000) // Se ejecuta cada 3 horas
    @Transactional
    public void ExecuteRemeberDate() {
        try{
            scheduleServiceServices._RememberDateSchedule();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
