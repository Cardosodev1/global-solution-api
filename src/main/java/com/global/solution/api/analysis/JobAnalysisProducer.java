package com.global.solution.api.analysis;

import com.global.solution.api.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobAnalysisProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendAnalysisJobToQueue(Long jobAnalysisId) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ANALYSIS, jobAnalysisId);
    }

}
