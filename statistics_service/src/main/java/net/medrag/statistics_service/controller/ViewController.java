package net.medrag.statistics_service.controller;

import lombok.val;
import net.medrag.statistics_service.service.StatisticsService;
import net.medrag.statistics_service.service.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/reset", method = {RequestMethod.DELETE, RequestMethod.POST})
    @ResponseBody
    public String reset() {
        statisticsService.resetStatistics();
        return "OK";
    }

    @GetMapping("/{amount}")
    public String getStats(@PathVariable int amount, Model model) {
        val lastGetStatsRecords = statisticsService.getLastGetAmountStatsRecords(amount);
        val lastAddStatsRecords = statisticsService.getLastAddAmountStatsRecords(amount);
        model.addAttribute("getStats", lastGetStatsRecords);
        model.addAttribute("addStats", lastAddStatsRecords);
        return "index.html";
    }
}
