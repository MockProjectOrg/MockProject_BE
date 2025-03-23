package org.example.bookingbe.service.MailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Component
public class MailRegister {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailRegister(String email, String firstName, String lastName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/client/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("name", firstName + " " + lastName);
        String htmlContent = templateEngine.process("emailRegister", context);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Thông báo đăng ký tài khoản thành công");
        mimeMessageHelper.setText(htmlContent, true);

        // Gửi email
        mailSender.send(mimeMessage);
    }
}