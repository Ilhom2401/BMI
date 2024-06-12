package com.example.buildings.controller;

import com.example.buildings.dto.ProductDto;
import com.example.buildings.dto.TaskDto;
import com.example.buildings.entity.*;
import com.example.buildings.service.EmployeeService;
import com.example.buildings.service.ProjectService;
import com.example.buildings.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final TaskService taskService;

    @PostMapping("/get-tasks")
    public String getList(@ModelAttribute("userId") Long userId, Model model) {
        boolean access = employeeService.checkAccess("GET_LIST_PROJECT", userId);
        if (!access)
            return "not-found";
        Employee employee = employeeService.getById(userId);
        List<Task> allTasks = taskService.getAllTasks();
        List<TaskDto> taskDtoModel = getTaskDtos(allTasks);
        model.addAttribute("list", taskDtoModel);
        model.addAttribute("empId", userId);
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin-tasks";
    }

    private static List<TaskDto> getTaskDtos(List<Task> allTasks) {
        List<TaskDto> taskDtoModel = new ArrayList<>();
        for (Task allTask : allTasks) {
            TaskDto taskDto1 = new TaskDto();
            taskDto1.setId(allTask.getId());
            taskDto1.setTaskName(allTask.getName());
            taskDto1.setCompletedPercent(allTask.getPercentCompleted() + " %");
            taskDto1.setProjectName(allTask.getProject().getName());
            taskDto1.setEndDate(allTask.getEndDate());
            taskDto1.setCompleted(allTask.getPercentCompleted() == 100);
            taskDto1.setCreatedDate(allTask.getCreatedDate());
            taskDto1.setCompletedDate(allTask.getCompletedDate());
            taskDtoModel.add(taskDto1);
        }
        return taskDtoModel;
    }

    @PostMapping("/add-task")
    public String addProject(@ModelAttribute("userId") Long userId, Model model) {
        boolean access = employeeService.checkAccess("ADD_PRODUCT", userId);
        if (!access) return "not-found";
        List<Project> projectList = projectService.getList();
        model.addAttribute("empId", userId);
        model.addAttribute("projectList", projectList);
        return "add-task";
    }

    @PostMapping("/save-task")
    public String save(@ModelAttribute TaskDto taskDto, Model model) {
        boolean access = employeeService.checkAccess("ADD_PRODUCT", taskDto.getUserId());
        if (!access) return "not-found";
        taskService.save(taskDto);
        return getString(taskDto, model);
    }

    @PostMapping("/edit-task")
    public String editTask(@ModelAttribute TaskDto taskDto, Model model) {
        boolean access = employeeService.checkAccess("ADD_PRODUCT", taskDto.getUserId());
        if (!access) return "not-found";
        Task task = taskService.getTaskById(taskDto.getId());
        model.addAttribute("empId", taskDto.getUserId());
        model.addAttribute("task", task);
        return "set-task-percent";
    }

    @PostMapping("/set-task-percent")
    public String saveTaskPercent(@ModelAttribute TaskDto taskDto, Model model) {
        boolean access = employeeService.checkAccess("ADD_PRODUCT", taskDto.getUserId());
        if (!access) return "not-found";
        if (taskDto.getPercent() < 0 || taskDto.getPercent() > 100)
            return "percent-error";
        taskService.setTaskPercent(taskDto);
        return getString(taskDto, model);
    }

    private String getString(@ModelAttribute TaskDto taskDto, Model model) {
        Employee employee = employeeService.getById(taskDto.getUserId());
        List<Task> allTasks = taskService.getAllTasks();
        List<TaskDto> taskDtoModel = getTaskDtos(allTasks);
        model.addAttribute("list", taskDtoModel);
        model.addAttribute("empId", taskDto.getUserId());
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin-tasks";
    }
}
