package app.service.email;

import app.model.email.EmailSettings;
import app.model.email.EmailSettingsRepo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Properties;


@Service
public class EmailService {


    public static String emailErrors;

    private final EmailSettingsRepo emailSettingsRepo;


    public EmailService(EmailSettingsRepo emailSettingsRepo) {

        this.emailSettingsRepo = emailSettingsRepo;
    }


    public synchronized void sendEmail(String message,String subject, String toEmail) {

        try {

            emailErrors = "";

            if (!toEmail.trim().equals("")) {

                SimpleMailMessage email = new SimpleMailMessage();

                email.setFrom(getEmailSettings().getUsername());
                email.setTo(toEmail);
                email.setSubject(subject);
                email.setText(message);

                getJavaMailSender().send(email);

            }


        } catch (Exception e) {

            emailErrors = e.getMessage();
        }


    }


    private JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        EmailSettings emailProps = getEmailSettings();

        javaMailSender.setHost(emailProps.getHost());
        javaMailSender.setPort(emailProps.getPort());

        javaMailSender.setUsername(emailProps.getUsername());
        javaMailSender.setPassword(emailProps.getPassword());


        Properties props = javaMailSender.getJavaMailProperties();

        props.put("mail.transport.protocol", "smtp");

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");


        return javaMailSender;

    }

    private EmailSettings getEmailSettings() {

        EmailSettings emailSettings = emailSettingsRepo.findFirstBy();

        return Objects.requireNonNullElseGet(emailSettings, EmailSettings::new);

    }


}