package kg.project.megatest.service.impl;

import kg.project.megatest.entity.Task;
import kg.project.megatest.entity.User;
import kg.project.megatest.repository.TaskRepository;
import kg.project.megatest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TaskRepository taskRepository;

    public void sendTaskByEmail(Long taskId, String recipientEmail) throws MessagingException {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new EntityNotFoundException("Task not found");
        }

        Task task = taskOptional.get();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("xoshanlo@mail.ru");
        helper.setTo(recipientEmail);
        helper.setSubject("Задача: " + task.getName());
        helper.setText("Информация о задаче:\n\n" +
                "Название: " + task.getName() + "\n" +
                "Описание: " + task.getDescription() + "\n" +
                "Дата создания: " + task.getCreationDate());

        mailSender.send(message);
    }

}