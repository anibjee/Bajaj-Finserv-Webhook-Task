package com.bajaj.webhook.service;

import com.bajaj.webhook.model.SolutionRequest;
import com.bajaj.webhook.model.WebhookRequest;
import com.bajaj.webhook.model.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.webhook.generate.url}")
    private String generateWebhookUrl;

    @Value("${api.webhook.submit.url}")
    private String submitWebhookUrl;

    /**
     * Generates webhook and returns the response containing webhook URL and access
     * token
     */
    public WebhookResponse generateWebhook(WebhookRequest request) {
        try {
            log.info("Generating webhook for user: {}", request.getName());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<WebhookResponse> response = restTemplate.exchange(
                    generateWebhookUrl,
                    HttpMethod.POST,
                    entity,
                    WebhookResponse.class);

            WebhookResponse webhookResponse = response.getBody();

            if (webhookResponse != null) {
                log.info("Webhook generated successfully");
                log.debug("Webhook URL: {}", webhookResponse.getWebhook());
                log.debug("Access Token: {}", webhookResponse.getAccessToken());
            }

            return webhookResponse;

        } catch (Exception e) {
            log.error("Error generating webhook: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate webhook", e);
        }
    }

    /**
     * Submits the SQL solution to the webhook URL using JWT authentication
     */
    public String submitSolution(String webhookUrl, String accessToken, String sqlQuery) {
        try {
            log.info("Submitting solution to webhook");
            log.debug("SQL Query: {}", sqlQuery);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);

            SolutionRequest solutionRequest = new SolutionRequest(sqlQuery);
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(solutionRequest, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    webhookUrl,
                    HttpMethod.POST,
                    entity,
                    String.class);

            String responseBody = response.getBody();
            log.info("Solution submitted successfully");
            log.info("Response: {}", responseBody);

            return responseBody;

        } catch (Exception e) {
            log.error("Error submitting solution: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to submit solution", e);
        }
    }
}
