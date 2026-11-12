
package Task.Management.API.service;

import Task.Management.API.entity.Task;
import Task.Management.API.entity.User;
import Task.Management.API.entity.TaskStatus;
import Task.Management.API.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    // Test 1: Save task
    @Test
    void shouldSaveTask() {

        Task task = new Task();
        User user = new User();

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.saveTask(task, user);

        assertEquals(user, result.getOwner());
        verify(taskRepository).save(task);
    }

    // Test 2: Find existing task
    @Test
    void shouldFindTaskById() {

        Task task = new Task();
        User user = new User();

        when(taskRepository.findByIdAndOwner(1L, user))
                .thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L, user);

        assertNotNull(result);
        verify(taskRepository).findByIdAndOwner(1L, user);
    }

    // Test 3: Task not found
    @Test
    void shouldThrowExceptionWhenTaskNotFound() {

        User user = new User();

        when(taskRepository.findByIdAndOwner(1L, user))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                taskService.getTaskById(1L, user));
    }

    // Test 4: Update task
    @Test
    void shouldUpdateTask() {

        Task existingTask = new Task();
        existingTask.setTitle("Old title");

        Task updatedTask = new Task();
        updatedTask.setTitle("New title");

        User user = new User();

        when(taskRepository.findByIdAndOwner(1L, user))
                .thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask))
                .thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTask, user);

        assertEquals("New title", result.getTitle());
    }

    // Test 5: Delete task
    @Test
    void shouldDeleteTask() {

        Task task = new Task();
        User user = new User();

        when(taskRepository.findByIdAndOwner(1L, user))
                .thenReturn(Optional.of(task));

        taskService.deleteTask(1L, user);

        verify(taskRepository).delete(task);
    }
}
