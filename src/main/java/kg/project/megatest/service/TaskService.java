package kg.project.megatest.service;

import kg.project.megatest.model.request.TaskRequest;
import kg.project.megatest.model.response.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse create(TaskRequest request);
    TaskResponse findById(Long id);
    TaskResponse update(TaskRequest request, Long id);
    List<TaskResponse> findAll() ;
    void deleteById(Long id);

}
