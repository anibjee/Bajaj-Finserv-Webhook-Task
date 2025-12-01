package com.bajaj.webhook.runner;

import com.bajaj.webhook.model.WebhookRequest;
import com.bajaj.webhook.model.WebhookResponse;
import com.bajaj.webhook.service.SqlSolutionService;
import com.bajaj.webhook.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WebhookRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(WebhookRunner.class);

    @Autowired
    private WebhookService webhookService;

    @Autowired
    private SqlSolutionService sqlSolutionService;

    @Value("${user.name}")
    private String userName;

    @Value("${user.regNo}")
    private String regNo;

    @Value("${user.email}")
    private String email;

    @Override
    public void run(String... args) throws Exception {
        log.info("=================================================");
        log.info("Starting Webhook SQL Challenge");
        log.info("=================================================");

        try {
            // Step 1: Generate webhook
            log.info("Step 1: Generating webhook...");
            WebhookRequest request = new WebhookRequest(userName, regNo, email);
            WebhookResponse webhookResponse = webhookService.generateWebhook(request);

            if (webhookResponse == null || webhookResponse.getWebhook() == null) {
                log.error("Failed to generate webhook - response is null");
                return;
            }

            // Step 2: Get SQL solution
            log.info("Step 2: Preparing SQL solution...");
            String sqlQuery = sqlSolutionService.getSqlSolution(regNo);
            log.info("SQL Query prepared successfully");

            // Step 3: Submit solution
            log.info("Step 3: Submitting solution to webhook...");
            String response = webhookService.submitSolution(
                    webhookResponse.getWebhook(),
                    webhookResponse.getAccessToken(),
                    sqlQuery);

            log.info("=================================================");
            log.info("Challenge completed successfully!");
            log.info("Final Response: {}", response);
            log.info("=================================================");

        } catch (Exception e) {
            log.error("=================================================");
            log.error("Error during webhook challenge execution: {}", e.getMessage(), e);
            log.error("=================================================");
        }
    }
}
