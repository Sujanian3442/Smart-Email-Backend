package com.email.writer.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
//@RequestMapping("/api/email")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class EmailGeneratorController {

    private EmailGeneratorService emailGeneratorService;

    //@PostMapping("/generate")
    @GetMapping("/")
    public String home() {
        return "Smart Email Backend is running 🚀";
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateEmail(@RequestBody EmailRequest emailRequest) {
        // String response = emailGeneratorService.generateEmailReply(emailRequest);
        // return ResponseEntity.ok(response);
        try {
        String response = emailGeneratorService.generateEmailReply(emailRequest);
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity
            .status(429)
            .body("AI quota exceeded. Please try again later.");
    }
    }

}
