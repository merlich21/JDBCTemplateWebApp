package ru.skillfactory.DAO;

import ru.skillfactory.entites.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    List<Department> findAll();
    Optional<Department> findById(int id);
    void save(Department dept);
    void deleteById(int id);
}
