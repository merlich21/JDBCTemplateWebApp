package ru.skillfactory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillfactory.DAO.DepartmentRepository;
import ru.skillfactory.DAO.EmployeeRepository;
import ru.skillfactory.DTO.EmployeeInfoDTO;
import ru.skillfactory.entites.Department;
import ru.skillfactory.entites.Employee;
import ru.skillfactory.mappers.EmployeeMapperImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepo,
                               DepartmentRepository departmentRepo) {
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepo.findById(id);
    }

    @Override
    public List<EmployeeInfoDTO> getAllEmployeesWithDept() {

        List<Employee> employees = employeeRepo.findAll();

        Map<Integer, String> deptNames = departmentRepo.findAll().stream()
                .collect(Collectors.toMap(Department::getId, Department::getName));


        List<EmployeeInfoDTO> result = new ArrayList<>();

        for (Employee e : employees) {

            result.add(
                    EmployeeMapperImpl.toDto(e, deptNames.get(e.getDepartmentId())
                    )
            );
        }

        return result;
    }

    @Override
    public Employee createOrUpdateEmployee(Employee employee) {
        // Здесь можно добавить логику проверки или преобразования, если нужно
        return employeeRepo.save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepo.deleteById(id);
    }


}
