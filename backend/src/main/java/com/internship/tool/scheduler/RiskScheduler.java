package com.internship.tool.scheduler;
import com.internship.tool.service.RiskService;
import com.internship.tool.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RiskScheduler {

    @Autowired
    private RiskService riskService;

    // Daily check for overdue risks
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkOverdueRisks() {
        riskService.handleOverdueRisks();
    }

    // Weekly summary
    @Scheduled(cron = "0 0 10 ? * MON")
    public void weeklySummary() {
        System.out.println("Weekly summary triggered");
    }
}