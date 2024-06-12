package com.example.buildings.controller;

import com.example.buildings.dto.ProjectDto;
import com.example.buildings.entity.Employee;
import com.example.buildings.entity.EmployeePosition;
import com.example.buildings.entity.Project;
import com.example.buildings.entity.RoleEntity;
import com.example.buildings.service.EmployeeService;
import com.example.buildings.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @PostMapping("/get-projects")
    public String getList(@ModelAttribute("userId") Long userId, Model model){
        boolean access = employeeService.checkAccess("GET_LIST_PROJECT", userId);
        if (!access)
            return "not-found";
        Employee employee = employeeService.getById(userId);
        List<Project> list = projectService.getList();
        model.addAttribute("list", list);
        model.addAttribute("empId", userId);
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin-projects";
    }

    @PostMapping("/add-project")
    public String addProject(@ModelAttribute("userId") Long userId, Model model){
        boolean access = employeeService.checkAccess("ADD_PROJECT", userId);
        if (!access)
            return "not-found";
        model.addAttribute("empId", userId);
        return "add-project";
    }

    @PostMapping("save-project")
    public String save(@ModelAttribute ProjectDto projectDto, Model model){
        boolean access = employeeService.checkAccess("ADD_PROJECT", projectDto.getUserId());
        if (!access)
            return "not-found";
        projectService.save(projectDto);
        return getString(projectDto, model);
    }

    @PostMapping("/edit-project")
    public String edit(@ModelAttribute ProjectDto projectDto, Model model){
        boolean access = employeeService.checkAccess("EDIT_PROJECT", projectDto.getUserId());
        if (!access)
            return "not-found";
        Project project = projectService.edit(projectDto);
        model.addAttribute("empId", projectDto.getUserId());
        model.addAttribute("project", project);
        return "edit-project";
    }

    @PostMapping("/edited-project")
    public String edited(@ModelAttribute ProjectDto projectDto, Model model){
        boolean access = employeeService.checkAccess("EDIT_PROJECT", projectDto.getUserId());
        if (!access)
            return "not-found";
        projectService.edited(projectDto);
        return getString(projectDto, model);
    }

    private String getString(@ModelAttribute ProjectDto projectDto, Model model) {
        List<Project> list = projectService.getList();
        Employee employee = employeeService.getById(projectDto.getUserId());
        model.addAttribute("list", list);
        model.addAttribute("empId", projectDto.getUserId());
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin-projects";
    }
}
