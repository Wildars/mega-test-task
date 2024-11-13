package kg.project.megatest;

import kg.project.megatest.entity.Task;
import kg.project.megatest.mapper.TaskMapper;
import kg.project.megatest.model.request.TaskRequest;
import kg.project.megatest.model.response.TaskResponse;
import kg.project.megatest.repository.TaskRepository;
import kg.project.megatest.exception.RecordNotFoundException;
import kg.project.megatest.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskRequest taskRequest;
    private Task task;
    private TaskResponse taskResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестового объекта для TaskRequest
        taskRequest = new TaskRequest();
        taskRequest.setName("Test Task");
        taskRequest.setDescription("Description");

        // Создание тестового объекта для Task
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setDescription("Description");

        // Создание тестового объекта для TaskResponse
        taskResponse = new TaskResponse();
        taskResponse.setId(1L);
        taskResponse.setName("Test Task");
        taskResponse.setDescription("Description");
    }

    @Test
    void testCreate() {
        when(taskMapper.requestToEntity(taskRequest)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.entityToResponse(task)).thenReturn(taskResponse);

        TaskResponse createdTask = taskService.create(taskRequest);

        assertNotNull(createdTask);
        assertEquals(taskResponse.getId(), createdTask.getId());
        assertEquals(taskResponse.getName(), createdTask.getName());
        assertEquals(taskResponse.getDescription(), createdTask.getDescription());

        verify(taskRepository, times(1)).save(task);
        verify(taskMapper, times(1)).entityToResponse(task);
    }

    @Test
    void testFindById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.entityToResponse(task)).thenReturn(taskResponse);

        TaskResponse foundTask = taskService.findById(1L);

        assertNotNull(foundTask);
        assertEquals(taskResponse.getId(), foundTask.getId());
        assertEquals(taskResponse.getName(), foundTask.getName());
        assertEquals(taskResponse.getDescription(), foundTask.getDescription());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskMapper, times(1)).entityToResponse(task);
    }

    @Test
    void testFindById_ThrowsException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> taskService.findById(1L));

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate() {
        TaskRequest updatedRequest = new TaskRequest();
        updatedRequest.setName("Updated Task");
        updatedRequest.setDescription("Updated Description");

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).update(task, updatedRequest); // Изменено на doNothing для void методов
        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);
        when(taskMapper.entityToResponse(updatedTask)).thenReturn(taskResponse);

        TaskResponse updatedTaskResponse = taskService.update(updatedRequest, 1L);

        assertNotNull(updatedTaskResponse);
        assertEquals("Updated Task", updatedTaskResponse.getName());
        assertEquals("Updated Description", updatedTaskResponse.getDescription());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(updatedTask);
        verify(taskMapper, times(1)).entityToResponse(updatedTask);
    }


    @Test
    void testFindAll() {
        List<Task> taskList = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(taskList);
        when(taskMapper.entityToResponse(task)).thenReturn(taskResponse);

        List<TaskResponse> tasks = taskService.findAll();

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(taskResponse.getId(), tasks.get(0).getId());

        verify(taskRepository, times(1)).findAll();
        verify(taskMapper, times(1)).entityToResponse(task);
    }

    @Test
    void testDeleteById() {
        when(taskRepository.getById(1L)).thenReturn(task);

        taskService.deleteById(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
