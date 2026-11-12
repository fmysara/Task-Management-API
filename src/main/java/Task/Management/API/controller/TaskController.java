
package Task.Management.API.controller;

import Task.Management.API.entity.Task;
import Task.Management.API.entity.User;
import Task.Management.API.entity.TaskStatus;
import Task.Management.API.service.TaskService;
import Task.Management.API.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
        this.userRepository = userRepository;
    }

    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Create task
    @PostMapping
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody Task task,
            Authentication authentication) {

        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(taskService.saveTask(task, user));
    }

    // Get all tasks with pagination and optional status filter
    @GetMapping
    public ResponseEntity<?> getAllTasks(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status) {

        User user = getCurrentUser(authentication);

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                taskService.getAllTasks(user, status, pageable)
        );
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(taskService.getTaskById(id, user));
    }

    // Update task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody Task task,
            Authentication authentication) {

        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(taskService.updateTask(id, task, user));
    }

    // Delete task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            Authentication authentication) {

        User user = getCurrentUser(authentication);
        taskService.deleteTask(id, user);

        return ResponseEntity.noContent().build();
    }
}
