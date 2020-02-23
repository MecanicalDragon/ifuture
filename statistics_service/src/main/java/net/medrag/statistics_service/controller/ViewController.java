package net.medrag.statistics_service.controller;

import lombok.val;
import net.medrag.statistics_service.model.StatsResponse;
import net.medrag.statistics_service.service.StatisticsService;
import net.medrag.statistics_service.service.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Controller
@RequestMapping("/stats")
public class ViewController {

    private StatisticsService statisticsService;

    @Autowired
    public ViewController(StatisticsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }


    //TODO: make post
    @RequestMapping("/reset")
    @ResponseBody
    public String reset() {
        System.out.println("reset");
        statisticsService.resetStatistics();
        return "OK";
    }

    @RequestMapping("/{amount}")
    public String getStats(@PathVariable int amount, Model model) {
        val lastGetStatsRecords = statisticsService.getLastGetAmountStatsRecords(amount);
        val lastAddStatsRecords = statisticsService.getLastAddAmountStatsRecords(amount);
        model.addAttribute("getStats", lastGetStatsRecords);
        model.addAttribute("addStats", lastAddStatsRecords);
        return "index.html";
    }
}
