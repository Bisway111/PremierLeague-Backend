package com.premierLeague.premier_Zone.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private SendGridEmailService sendGridEmailService;

    public void sendWelcomemail(String to,String name)throws IOException {
        String subject = "Welcome to Premier League 🎉";

        String template = Files.readString(Paths.get("src/main/resources/templates/welcome-email.html"));

        template = template.replace("{{name}}",name);
        sendGridEmailService.sendEmail(to,subject,template);
         log.info(template);
    }
}
