// package com.email.writer.app;

// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;

// @Service
// public class EmailGeneratorService {

//     private final WebClient webClient;

//     @Value("${gemini.api.url}")
//     private String geminiApiUrl;

//     @Value("${gemini.api.key}")
//     private String geminiApiKey;

//     public EmailGeneratorService(WebClient.Builder webClientBuilder) {
//         this.webClient = webClientBuilder.build();
//     }

//     public String generateEmailReply(EmailRequest emailRequest) {
//         //Build the prompt
//         String prompt = buildPrompt(emailRequest);

//         //Craft a request
//         Map<String , Object> requestBody = Map.of(
//                 "contents" , new Object[] {
//                         Map.of("parts" , new Object[] {
//                                 Map.of("text" , prompt)
//                         })
//                 }
//         );

//         //Do request and get response
//         String response = webClient.post()
//                 .uri(geminiApiUrl + "?key=" + geminiApiKey)
//                 .header("Content-Type","application/json")
//                 .bodyValue(requestBody)
//                 .retrieve()
//                 .bodyToMono(String.class)
//                 .block();



//         //Extract response and Return
//         return  extractResponseContent(response);
//     }

//      private String extractResponseContent(String response) {
//          try {
//          ObjectMapper mapper = new ObjectMapper();
//              JsonNode rootNode = mapper.readTree(response);
//              return rootNode.path("candidates")
//                      .get(0)
//                      .path("content")
//                      .path("parts")
//                      .get(0)
//                      .path("text")
//                      .asText();
//          }catch (Exception e) {
//              return "Error processing request : " + e.getMessage();
//          }
//      }
    

//     private String buildPrompt(EmailRequest emailRequest) {
//         StringBuilder prompt = new StringBuilder();
//         prompt.append("Generate a professional email reply for the following content. Please don't generate a subject line.");
//         if(emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
//             prompt.append("Use a ").append(emailRequest.getTone()).append(" tone.");
//         }
//         prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
//         return prompt.toString();
//     }
// }



// -------------------------------------------------------------------------------------------------
package com.email.writer.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.api.key}")
    private String groqApiKey;

    public EmailGeneratorService(WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest) {

        String prompt = buildPrompt(emailRequest);

        Map<String, Object> requestBody = Map.of(
            "model", "openai/gpt-oss-20b",
            "messages", List.of(
                Map.of(
                    "role", "system",
                    "content", "You are a professional email assistant."
                ),
                Map.of(
                    "role", "user",
                    "content", prompt
                )
            )
        );

        String response = webClient.post()
            .uri(groqApiUrl)
            .header("Authorization", "Bearer " + groqApiKey)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            return root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        } catch (Exception e) {
            return "Error parsing Groq response: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Write a professional email reply.");
        if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append(" Use a ").append(emailRequest.getTone()).append(" tone.");
        }
        prompt.append("\n\nOriginal email:\n");
        prompt.append(emailRequest.getEmailContent());

        return prompt.toString();
    }
}
