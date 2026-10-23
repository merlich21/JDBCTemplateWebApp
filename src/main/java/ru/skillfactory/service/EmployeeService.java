package ru.skillfactory.service;

import ru.skillfactory.DTO.EmployeeInfoDTO;
import ru.skillfactory.entites.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(int id);
    List<EmployeeInfoDTO> getAllEmployeesWithDept();
    Employee createOrUpdateEmployee(Employee employee);
    void deleteEmployee(int id);
}
