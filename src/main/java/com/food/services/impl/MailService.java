package com.food.services.impl;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendResetPasswordEmail(String to, String newPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Password Reset Request");
            helper.setText(
                    "<p>Hello,</p>"
                            + "<p>Your new password is: <b>" + newPassword + "</b></p>"
                            + "<p>Please log in and change your password immediately.</p>",
                    true
            );

            mailSender.send(message);
            log.info("Reset password email sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send reset password email to {}", to, e);
            throw new RuntimeException("Unable to send email");
        }
    }
}
