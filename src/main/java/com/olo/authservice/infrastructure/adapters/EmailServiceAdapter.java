package com.olo.authservice.infrastructure.adapters;

import com.olo.authservice.domain.command.EmailCommand;
import com.olo.authservice.domain.exception.email.MailAuthException;
import com.olo.authservice.domain.exception.email.MimeMessageHelperException;
import com.olo.authservice.domain.port.outbound.EmailServicePort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import com.olo.authservice.domain.exception.email.MessageSendingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@RequiredArgsConstructor
public class EmailServiceAdapter implements EmailServicePort {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailCommand emailCommand) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailCommand.to());
            helper.setSubject(emailCommand.subject());
            helper.setText(emailCommand.body(), true);

            javaMailSender.send(message);
        } catch (MailAuthenticationException e) {
            throw new MailAuthException("Error with email credentials:", e);
        } catch (MailSendException e) {
            throw new MessageSendingException("Error sending email:", e);
        } catch (MessagingException e) {
            throw new MimeMessageHelperException("Error with the message helper:", e);
        }
    }
}
