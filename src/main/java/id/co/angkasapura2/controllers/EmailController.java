package id.co.angkasapura2.controllers;

import id.co.angkasapura2.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public boolean testingSendEmail(@RequestBody Map map){
        String to = (String) map.get("to");
        String subject = (String) map.get("subject");
        String body = (String) map.get("body");
        try {
            emailService.sendEmail(to, subject, body);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
