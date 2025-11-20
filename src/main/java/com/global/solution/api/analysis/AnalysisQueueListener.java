package com.global.solution.api.analysis;

import com.global.solution.api.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AnalysisQueueListener {

    private final JobAnalysisService jobAnalysisService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ANALYSIS)
    public void handleAnalysisRequest(Long jobAnalysisId) {
        log.info("Recebido pedido de análise para o Job ID: {}", jobAnalysisId);
        try {
            jobAnalysisService.processAnalysisJob(jobAnalysisId);
            log.info("Processamento do Job ID {} concluído com sucesso.", jobAnalysisId);
        } catch (Exception e) {
            log.error("Erro fatal ao processar Job ID {}. Mensagem não será reenviada.", jobAnalysisId, e);
        }
    }

}
