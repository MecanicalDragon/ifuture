package net.medrag.test_client.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

class AddAmountRequestSender extends RecursiveAction {

    private RestTemplate restTemplate;

    public AddAmountRequestSender(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private String workload = "";
    private static final int THRESHOLD = 10;

    AddAmountRequestSender(String workload) {
        this.workload = workload;
    }

    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(workload);
        }
    }

    private List<AddAmountRequestSender> createSubtasks() {
        List<AddAmountRequestSender> subtasks = new ArrayList<>();
        int begin = 0, end = 10;
        while (begin < workload.length()) {
            int realEnd = Math.min(end, workload.length());
            subtasks.add(new AddAmountRequestSender(workload.substring(begin, realEnd)));
            begin += 10;
            end += 10;
        }
        return subtasks;
    }

    private void processing(String work) {
        String result = work.toLowerCase();
        System.out.println("Action result - (" + result + ") - was processed by " + Thread.currentThread().getName());
    }
}

