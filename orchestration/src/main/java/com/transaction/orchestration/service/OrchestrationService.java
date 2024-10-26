package com.transaction.orchestration.service;

import org.springframework.stereotype.Service;

import com.transaction.orchestration.dto.Alldto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;

@Service
public class OrchestrationService {

    private final StreamBridge streamBridge;

    private final Logger log = LoggerFactory.getLogger(OrchestrationService.class);

    public OrchestrationService(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishPostEvent(Alldto alldto) {
        log.info("Publishing post event: {}", alldto);
        streamBridge.send("post-out-0",  alldto.postWriteDto());
        log.info("Post event published: {}", alldto);
    }
    
    public void publishPlanEvent(Alldto alldto) {
        log.info("Publishing plan event: {}", alldto);
        streamBridge.send("plan-out-0",  alldto.plandto());
        log.info("plan event published: {}", alldto);
    }

    public void publishBudgetEvent(Alldto alldto) {
        log.info("Publishing budget event: {}", alldto);
        streamBridge.send("budget-out-0",  alldto.budget());
        log.info("budget event published: {}", alldto);
    }

    
}
