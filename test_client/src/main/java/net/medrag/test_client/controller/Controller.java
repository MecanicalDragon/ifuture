package net.medrag.test_client.controller;

import net.medrag.test_client.service.api.ProcessorApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private ProcessorApi processor;

    @Autowired
    public Controller(ProcessorApi processor) {
        this.processor = processor;
    }

    @RequestMapping("/potDoNotCook")
    public String silence(){
        processor.stop();
        return "Stopping...";
    }
}
