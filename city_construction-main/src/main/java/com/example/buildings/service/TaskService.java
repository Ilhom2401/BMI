package com.example.buildings.service;

import com.example.buildings.dto.ProductDto;
import com.example.buildings.dto.TaskDto;
import com.example.buildings.entity.*;
import com.example.buildings.repository.ProjectRepository;
import com.example.buildings.repository.RoleRepository;
import com.example.buildings.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public boolean save(TaskDto taskDto) {
        if (taskDto.getProjectId() == null)
            return false;
        Task task = new Task();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Project project = projectRepository.getById(taskDto.getProjectId());
        task.setName(taskDto.getTaskName());
        task.setProject(project);
        task.setEndDate(taskDto.getEndDate());
        task.setPercentCompleted(0L);
        task.setCreatedDate(sdf.format(cal.getTime()).toString());
        taskRepository.save(task);
        return true;
    }

    public Task getTaskById(Long taskId) {
        Optional<Task> byId = taskRepository.findById(taskId);
        return byId.get();
    }

    public boolean setTaskPercent(TaskDto taskDto) {
        Optional<Task> byId = taskRepository.findById(taskDto.getId());
        Task task = byId.get();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        task.setPercentCompleted(taskDto.getPercent());
        if (taskDto.getPercent() == 100)
            task.setCompletedDate(sdf.format(cal.getTime()).toString());
        taskRepository.save(task);
        return true;
    }
}
