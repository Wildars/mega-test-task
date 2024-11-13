package kg.project.megatest.controller;

import kg.project.megatest.model.request.TaskRequest;
import kg.project.megatest.model.response.TaskResponse;
import kg.project.megatest.service.TaskService;
import kg.project.megatest.service.impl.EmailService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    TaskService taskService;

    EmailService emailService;

    public TaskController(TaskService taskService, EmailService emailService) {
        this.taskService = taskService;
        this.emailService = emailService;
    }

    @PostMapping("/save")
    public ResponseEntity<TaskResponse> save(@Valid @RequestBody TaskRequest request){
        TaskResponse save = taskService.create(request);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<TaskResponse> findAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        TaskResponse getRoleById = taskService.findById(id);
        return new ResponseEntity<>(getRoleById, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskResponse> update(@Valid @RequestBody TaskRequest request, @PathVariable Long id) {
        TaskResponse updated = taskService.update(request, id);
        return new ResponseEntity<>(updated, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleted/{id}")
    public void deleted(@PathVariable Long id) {
        taskService.deleteById(id);
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendDocument(
            @RequestParam Long taskId,
            @RequestParam String recipientEmail) {
        try {
            emailService.sendTaskByEmail( taskId,  recipientEmail);
            return ResponseEntity.ok("Докусент успешно отправлен " + recipientEmail);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User не найден");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("не получилсоь отправить документ");
        }
    }
}
