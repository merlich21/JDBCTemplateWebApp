package ru.skillfactory.DAO;


import org.springframework.stereotype.Repository;
import ru.skillfactory.DTO.EmployeeInfoDTO;
import ru.skillfactory.entites.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository {

    List<Employee> findAll();
    Optional<Employee> findById(int id);
    Employee save(Employee employee);
//    void update(Employee employee);
    void deleteById(int id);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByAgeGreater(int minAge);
    List<Employee> findByLastNameAndAgeRange(String lastName, int minAge, int maxAge);
    List<EmployeeInfoDTO> findAllWithDepartment();
}
