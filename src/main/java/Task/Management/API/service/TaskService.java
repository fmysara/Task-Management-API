
package Task.Management.API.service;

import Task.Management.API.entity.Task;
import Task.Management.API.entity.User;
import Task.Management.API.entity.TaskStatus;
import Task.Management.API.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task, User user) {
        task.setOwner(user);
        return taskRepository.save(task);
    }

    public Page<Task> getAllTasks(User user, TaskStatus status, Pageable pageable) {

        if (status != null) {
            return taskRepository.findByOwnerAndStatus(user, status, pageable);
        }

        return taskRepository.findByOwner(user, pageable);
    }

    public Task getTaskById(Long id, User user) {
        return taskRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long id, Task updatedTask, User user) {

        Task task = getTaskById(id, user);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setDueDate(updatedTask.getDueDate());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, User user) {
        Task task = getTaskById(id, user);
        taskRepository.delete(task);
    }
}
