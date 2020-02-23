package net.medrag.statistics_service.controller;

import lombok.val;
import net.medrag.statistics_service.model.StatsResponse;
import net.medrag.statistics_service.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    private StatService statService;

    @Autowired
    public ViewController(StatService statService) {
        this.statService = statService;
    }

//    @RequestMapping("/")
//    public String index() {
//        return "index.html";
//    }

    //TODO: make post
    @RequestMapping("/reset")
    @ResponseBody
    public String reset() {
        statService.resetStatistics();
        return "OK";
    }

    @RequestMapping("/{amount}")
    @ResponseBody
    public StatsResponse getStats(@PathVariable int amount) {
        val lastGetStatsRecords = statService.getLastGetAmountStatsRecords(amount);
        val lastAddStatsRecords = statService.getLastAddAmountStatsRecords(amount);
        return new StatsResponse(lastGetStatsRecords.toList(), lastAddStatsRecords.toList());
    }
}
