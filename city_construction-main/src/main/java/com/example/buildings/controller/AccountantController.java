package com.example.buildings.controller;

import com.example.buildings.dto.AccountantDto;
import com.example.buildings.entity.Employee;
import com.example.buildings.entity.Project;
import com.example.buildings.service.AccountantService;
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
public class AccountantController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final AccountantService accountantService;

    @PostMapping("/get-employess-accountant")
    public String getList(@ModelAttribute AccountantDto accountantDto, Model model){
        boolean access = employeeService.checkAccess("GET_LIST_EMPLOYEE", accountantDto.getUserId());
        if (!access)
            return "not-found";
        Employee employee = employeeService.getById(accountantDto.getUserId());
        List<Employee> employeeList = employeeService.getByProjectId(accountantDto.getProjectId());
        Project project = projectService.getById(accountantDto.getProjectId());
        List<Project> projectList = projectService.getList();
        model.addAttribute("empId", employee.getId());
        model.addAttribute("projectName", project.getName());
        model.addAttribute("project", employee.getProject());
        model.addAttribute("projectList", projectList);
        model.addAttribute("roleId", employee.getRole().getId());
        model.addAttribute("list", employeeList);
        return "employees-accountant";
    }
    @PostMapping("/get-admin-accountants")
    public String getAccountantList(@ModelAttribute("userId") Long userId, Model model) {
        boolean access = employeeService.checkAccess("GET_LIST_EMPLOYEE", userId);
        if (!access)
            return "not-found";
        Employee employee = employeeService.getById(userId);
        List<Employee> list = employeeService.getAccountantList();
        model.addAttribute("list", list);
        model.addAttribute("empId", userId);
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin-accountants";
    }

    @PostMapping("/edit-salary")
    public String editSalary(@ModelAttribute AccountantDto accountantDto, Model model){
        boolean access = employeeService.checkAccess("SET_SALARY", accountantDto.getUserId());
        if (!access)
            return "not-found";
        Employee employee = employeeService.getById(accountantDto.getEmployeeId());
        model.addAttribute("empId", accountantDto.getUserId());
        model.addAttribute("employee", employee);
        return "edit-salary";
    }

    @PostMapping("/edited-salary")
    public String edit(@ModelAttribute AccountantDto accountantDto, Model model){
        boolean access = employeeService.checkAccess("SET_SALARY", accountantDto.getUserId());
        if (!access)
            return "not-found";
        accountantService.edit(accountantDto);
        Employee employee = employeeService.getById(accountantDto.getUserId());
        List<Employee> employeeList = employeeService.getByProjectId(accountantDto.getProjectId());
        Project project = projectService.getById(accountantDto.getProjectId());
        List<Project> projectList = projectService.getList();
        model.addAttribute("empId", employee.getId());
        model.addAttribute("projectName", project.getName());
        model.addAttribute("project", employee.getProject());
        model.addAttribute("projectList", projectList);
        model.addAttribute("roleId", employee.getRole().getId());
        model.addAttribute("list", employeeList);
        return "employees-accountant";
    }
}
