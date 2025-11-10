package spring.hibernate.learning.LearningApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.hibernate.learning.LearningApp.entity.SubmissionEntity;

/**
 * Сервис рассылки сообщений электронной почты
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailNotificationService {

    private static final String SUBJECT = "Задание оценено";

    private final JavaMailSender emailSender;

    /**
     * Отправка оценки задания студенту через Email
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean sendNotification(final SubmissionEntity submission) {
        try {
            final var simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(submission.getStudent().getProfile().getEmail());
            simpleMailMessage.setSubject(SUBJECT);
            simpleMailMessage.setText(String.format("Задание %s оценено: %.2f",
                    submission.getAssignment().getTitle(), submission.getScore()));
            emailSender.send(simpleMailMessage);

            log.info("Сообщение Email отправлено успешно");

            return true;
        } catch (Exception e) {
            log.error("Ошибка отправки Email: {}", e.getMessage());
            return false;
        }
    }
}
