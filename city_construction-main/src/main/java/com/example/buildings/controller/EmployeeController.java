package com.example.buildings.controller;

import com.example.buildings.dto.EditDto;
import com.example.buildings.dto.EmployeeDto;
import com.example.buildings.dto.LoginDto;
import com.example.buildings.entity.Employee;
import com.example.buildings.entity.EmployeePosition;
import com.example.buildings.entity.Project;
import com.example.buildings.entity.RoleEntity;
import com.example.buildings.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final GenericService genericService;
    private final RoleService roleService;
    private final EmployeePositionService employeePositionService;
    private final ProjectService projectService;

    @GetMapping("/")
    public String mainMenu() {
        return "index";
    }

    @PostMapping(value = "/login-user")
    public String add(@ModelAttribute LoginDto loginDto, Model model) {
        Employee employee = employeeService.getUserId(loginDto);
        if (employee == null)
            return "not-found";
//        if (employee.getRole().getId() == 5) {
//            model.addAttribute("employee", employee);
//            return "user";
//        }
        List<Project> projectList = projectService.getList();
        model.addAttribute("empName", employee.getFirstName() + " " + employee.getLastName());
        model.addAttribute("empId", employee.getId());
        model.addAttribute("project", employee.getProject());
        model.addAttribute("projectList", projectList);
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin";
    }

    @PostMapping("/get-employees")
    public String getEmpList(@ModelAttribute("userId") Long userId, Model model) {
        boolean access = employeeService.checkAccess("GET_LIST_EMPLOYEE", userId);
        if (!access)
            return "not-found";
        Employee employee = employeeService.getById(userId);
        List<Employee> list = employeeService.getList();
        model.addAttribute("list", list);
        model.addAttribute("empId", userId);
        model.addAttribute("roleId", employee.getRole().getId());
        return "admin-employees";
    }

    @PostMapping("/add-employee")
    public String addEmpl(@ModelAttribute("userId") Long userId, Model model) {
        boolean access = employeeService.checkAccess("ADD_EMPLOYEE", userId);
        if (!access)
            return "not-found";
        List<RoleEntity> roleEntityList = roleService.getList();
        List<EmployeePosition> positionList = employeePositionService.getList();
        List<Project> projectList = projectService.getList();
        model.addAttribute("empId", userId);
        model.addAttribute("roleList", roleEntityList);
        model.addAttribute("projectList", projectList);
        model.addAttribute("positionList", positionList);
        return "add-employee";
    }

    @PostMapping("save-employee")
    public String save(@ModelAttribute EmployeeDto employeeDto, Model model) {
        boolean access = employeeService.checkAccess("ADD_EMPLOYEE", employeeDto.getUserId());
        if (!access)
            return "not-found";
        employeeService.saveEmployee(employeeDto);
        List<RoleEntity> roleEntityList = roleService.getList();
        List<EmployeePosition> positionList = employeePositionService.getList();
        List<Employee> list = employeeService.getList();
        model.addAttribute("list", list);
        model.addAttribute("empId", employeeDto.getUserId());
        return "admin-employees";
    }

    @PostMapping("/edit-employee")
    public String edit(@ModelAttribute EditDto editDto, Model model) {
        boolean access = employeeService.checkAccess("EDIT_EMPLOYEE", editDto.getUserId());
        if (!access)
            return "not-found";
        List<RoleEntity> roleEntityList = roleService.getList();
        List<EmployeePosition> positionList = employeePositionService.getList();
        List<Project> projectList = projectService.getList();
        model.addAttribute("roleList", roleEntityList);
        model.addAttribute("positionList", positionList);
        model.addAttribute("projectList", projectList);
        Employee employee = employeeService.edit(editDto.getEmployeeId());
        model.addAttribute("empId", editDto.getUserId());
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/set-state-accountant")
    public String setState(@ModelAttribute EditDto editDto, Model model) {
        boolean access = employeeService.checkAccess("EDIT_EMPLOYEE", editDto.getUserId());
        if (!access)
            return "not-found";
        Employee employee = employeeService.edit(editDto.getEmployeeId());
        model.addAttribute("empId", editDto.getUserId());
        model.addAttribute("employeeId", employee.getId());
        model.addAttribute("employee_name", employee.getFirstName() + " " + employee.getLastName());
        model.addAttribute("employee_position", employee.getEmployeePosition().getName());
        return "set-state-accountant";
    }

    @PostMapping("/set-state")
    public String setState(@ModelAttribute EmployeeDto employeeDto, Model model) {
        boolean access = employeeService.checkAccess("EDIT_EMPLOYEE", employeeDto.getUserId());
        if (!access)
            return "not-found";
        employeeService.setState(employeeDto);
        List<Employee> list = employeeService.getAccountantList();
        model.addAttribute("list", list);
        model.addAttribute("empId", employeeDto.getUserId());
        return "admin-accountants";
    }

    @PostMapping("/edited-employee")
    public String edited(@ModelAttribute EmployeeDto employeeDto, Model model) {
        boolean access = employeeService.checkAccess("EDIT_EMPLOYEE", employeeDto.getUserId());
        if (!access)
            return "not-found";
        employeeService.edited(employeeDto);
        List<Employee> list = employeeService.getList();
        model.addAttribute("list", list);
        model.addAttribute("empId", employeeDto.getUserId());
        return "admin-employees";
    }

    @PostMapping("/edit-password")
    public String editPassword(@ModelAttribute EmployeeDto employeeDto, Model model) {
        boolean access = employeeService.checkAccess("EDIT_EMPLOYEE", employeeDto.getUserId());
        if (!access) {
            return "not-found";
        }
        if (!employeeDto.getNewPassword1().equals(employeeDto.getNewPassword2()) || employeeDto.getNewPassword2().isEmpty()) {
            return "password-invalid";
        }
        employeeService.editPassword(employeeDto);
        Employee employee = employeeService.getById(employeeDto.getUserId());
        model.addAttribute("employee", employee);
        model.addAttribute("empId", employeeDto.getUserId());
        model.addAttribute("roleId", employee.getRole().getId());
        model.addAttribute("project", employee.getProject());
        return "cabinet";
    }

    @PostMapping("/delete-employee")
    public String deleteEmployee(EditDto editDto, Model model) {
        boolean access = employeeService.checkAccess("DELETE_EMPLOYEE", editDto.getUserId());
        if (!access)
            return "not-found";
        employeeService.deleteEmployee(editDto.getEmployeeId());
        List<Employee> list = employeeService.getList();
        model.addAttribute("list", list);
        model.addAttribute("empId", editDto.getUserId());
        return "admin-employees";
    }

}
