package com.example.buildings.service;

import com.example.buildings.dto.EmployeeDto;
import com.example.buildings.dto.LoginDto;
import com.example.buildings.entity.*;
import com.example.buildings.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final SalaryRepository salaryRepository;
    private final EmployeePositionRepository employeePositionRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public boolean addEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        if (employeeDto.getRoleId() == null) {
            Optional<RoleEntity> byId = roleRepository.findById(5L);
            RoleEntity roleEntity = byId.get();
            employee.setRole(roleEntity);
        }
        employee.setActive(true);
        employeeRepository.save(employee);
        return true;
    }

    public List<Employee> getByProjectId(Long projectId) {
        return employeeRepository.getListByProjectId(projectId);
    }

    public List<Employee> getAccountantList() {
        return employeeRepository.getAccountantList();
    }

    public Employee getUserId(LoginDto loginDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        return optionalEmployee.orElse(null);
    }

    public Employee getById(Long userId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(userId);
        if (optionalEmployee.isEmpty())
            return null;
        return optionalEmployee.get();
    }

    public boolean checkAccess(String action, Long UserId) {
        Long access = employeeRepository.access(action, UserId);
        return access != null;
    }

    public List<Employee> getList() {
        return employeeRepository.getList();
    }

    public boolean saveEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getEmployeePositionId() == null)
            return false;
        if (employeeDto.getRoleId() == null)
            return false;
        Employee employee = new Employee();
        Salary salary = new Salary();
        salary.setSalary(employeeDto.getSalary());
        salary.setContractStartDate(employeeDto.getContractStartDate());
        salary.setContractEndDate(employeeDto.getContractEndDate());
        salary.setActive(true);
        Salary savedSalary = salaryRepository.save(salary);

        Optional<RoleEntity> optionalRole = roleRepository.findById(employeeDto.getRoleId());
        if (optionalRole.isEmpty())
            return false;
        RoleEntity roleEntity = optionalRole.get();

        Optional<Project> optionalProject = projectRepository.findById(employeeDto.getProjectId());
        if (optionalProject.isEmpty())
            return false;
        Project project = optionalProject.get();

        Optional<EmployeePosition> optionalEmployeePosition = employeePositionRepository.findById(employeeDto.getEmployeePositionId());
        if (optionalEmployeePosition.isEmpty())
            return false;
        EmployeePosition employeePosition = optionalEmployeePosition.get();

        employee.setRole(roleEntity);
        employee.setEmployeePosition(employeePosition);
        employee.setAddress(employeeDto.getAddress());
        employee.setProject(project);
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setSalary(savedSalary);
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setUsername(employeeDto.getUsername());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPassword(employeeDto.getPassword());

        employeeRepository.save(employee);
        return true;
    }

    public Employee edit(Long empId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        if (optionalEmployee.isEmpty())
            return null;
        return optionalEmployee.get();
    }

    public void setState(EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDto.getEmpId());
        Employee employee = optionalEmployee.get();
        employee.setActive(employeeDto.getStateId() == 1);
        employeeRepository.save(employee);
    }

    public void editPassword(EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDto.getUserId()); // ozini parolini ozgartiryapti
        Employee employee = optionalEmployee.get();
        employee.setPassword(employeeDto.getNewPassword1());
        employeeRepository.save(employee);
    }

    public boolean edited(EmployeeDto employeeDto) {
        if (employeeDto.getEmployeePositionId() == null)
            return false;
        if (employeeDto.getRoleId() == null)
            return false;
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDto.getEmpId());
//        if (optionalEmployee.isEmpty())
//            return false;

        Optional<RoleEntity> optionalRole = roleRepository.findById(employeeDto.getRoleId());
        if (optionalRole.isEmpty())
            return false;
        RoleEntity roleEntity = optionalRole.get();

        Optional<EmployeePosition> optionalEmployeePosition = employeePositionRepository.findById(employeeDto.getEmployeePositionId());
        if (optionalEmployeePosition.isEmpty())
            return false;
        EmployeePosition employeePosition = optionalEmployeePosition.get();

        Salary salary = new Salary();
        salary.setSalary(employeeDto.getSalary());
        salary.setContractStartDate(employeeDto.getContractStartDate());
        salary.setContractEndDate(employeeDto.getContractEndDate());
        salary.setActive(true);
        Salary savedSalary = salaryRepository.save(salary);

        new Project();
        Project project = projectService.getById(employeeDto.getProjectId());

        Employee employee = optionalEmployee.get();
        employee.setAddress(employeeDto.getAddress());
        employee.setBirthDate(employeeDto.getBirthDate());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setUsername(employeeDto.getUsername());
        employee.setPassword(employee.getPassword());
        employee.setEmployeePosition(employeePosition);
        employee.setRole(roleEntity);
        employee.setSalary(savedSalary);
        employee.setProject(project);
        employeeRepository.save(employee);
        return true;
    }

    public boolean deleteEmployee(Long empId) {
        employeeRepository.deleteById(empId);
        return true;
    }
}
