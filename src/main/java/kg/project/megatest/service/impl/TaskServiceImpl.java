package kg.project.megatest.service.impl;

import kg.project.megatest.entity.Task;
import kg.project.megatest.exception.RecordNotFoundException;
import kg.project.megatest.mapper.TaskMapper;
import kg.project.megatest.model.request.TaskRequest;
import kg.project.megatest.model.response.TaskResponse;
import kg.project.megatest.repository.TaskRepository;
import kg.project.megatest.service.TaskService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;

    TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskResponse create(TaskRequest request) {
        Task role = taskMapper.requestToEntity(request);
        Task savedRole = taskRepository.save(role);
        return taskMapper.entityToResponse(savedRole);
    }

    @Override
    public TaskResponse findById(Long id) {
        Task role = taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Task с таким id не существует!"));
        return taskMapper.entityToResponse(role);
    }

    @Override
    public TaskResponse update(TaskRequest request, Long id) {
        Task role = taskRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Task с таким id не существует"));
        taskMapper.update(role, request);
        Task updatedRole = taskRepository.save(role);
        return taskMapper.entityToResponse(updatedRole);
    }

    @Override
    public List<TaskResponse> findAll() {
        List<Task> roles = taskRepository.findAll();
        return roles.stream().map(taskMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        Task role = this.taskRepository.getById(id);
        taskRepository.deleteById(id);
    }


}
