package hexlet.code.service.impl;

import hexlet.code.dto.TaskDto;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskService;
import hexlet.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskStatusRepository taskStatusRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private LabelService labelService;

    @Override
    public Task createTask(final TaskDto taskDto) {
        Task task = new Task();
        User author = userService.getCurrentUser();
        TaskStatus taskStatus = taskStatusRepository.findById(taskDto.getTaskStatusId()).get();
        User executor = userRepository.findById(taskDto.getExecutorId()).get();
        List<Label> labels = taskDto.getLabelIds() == null ? null
                : labelRepository.findAllById(taskDto.getLabelIds());

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setAuthor(author);
        task.setTaskStatus(taskStatus);
        task.setExecutor(executor);
        task.setLabels(labels);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(final Long id, final TaskDto taskDto) {
        Task task = taskRepository.findById(id).get();
        User author = task.getAuthor();
        TaskStatus taskStatus = taskStatusRepository.findById(taskDto.getTaskStatusId()).get();
        User executor = userRepository.findById(taskDto.getExecutorId()).get();
        List<Label> labels = taskDto.getLabelIds() == null ? null
                : labelRepository.findAllById(taskDto.getLabelIds());

        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setAuthor(author);
        task.setTaskStatus(taskStatus);
        task.setExecutor(executor);
        task.setLabels(labels);
        return taskRepository.save(task);
    }
}
