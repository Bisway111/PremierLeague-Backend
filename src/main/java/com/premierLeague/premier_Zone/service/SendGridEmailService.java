package com.premierLeague.premier_Zone.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SendGridEmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridKey;

    @Value("${sendgrid.sender}")
    private String sender;

    public void sendEmail(String to , String subject, String htmlContent)throws IOException {
        Email fromEmail = new Email(sender,"Premier League");
        Email toEmail = new Email(to);
        Content content = new Content("text/html",htmlContent);

        Mail mail= new Mail(fromEmail,subject, toEmail,content);

        SendGrid sg = new SendGrid(sendGridKey);
        Request request = new Request();
        try{
          request.setMethod(Method.POST);
          request.setEndpoint("mail/send");
          request.setBody(mail.build());

          sg.api(request);
          log.info(String.valueOf(request));
        }catch (IOException ex){
           throw new IOException(ex);
        }
    }
}
