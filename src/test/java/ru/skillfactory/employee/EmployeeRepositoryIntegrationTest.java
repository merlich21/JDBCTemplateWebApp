package ru.skillfactory.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.skillfactory.DAO.EmployeeRepository;
import ru.skillfactory.DAO.EmployeeRepositoryImpl;
import ru.skillfactory.entites.Employee;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(EmployeeRepositoryImpl.class)
@ActiveProfiles("Test")
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private final EmployeeRepository employeeRepo;

    public EmployeeRepositoryIntegrationTest(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @BeforeEach
    void setupData() {
        // Можно через repository.save(...) или через JdbcTemplate,
        // если нужно загрузить исходные данные
        Employee emp1 = new Employee(1, "Ivan", "Ivanov", 30, 0);
        Employee emp2 = new Employee(2, "Petr", "Petrov", 25, 0);

        employeeRepo.save(emp1);
        employeeRepo.save(emp2);
    }


    @Test
    void testFindAll() {

        List<Employee> list = employeeRepo.findAll();
        assertThat(list).hasSize(2);
    }

    @Test
    void testFindByIdExists() {
        Optional<Employee> opt = employeeRepo.findById(1);
        assertThat(opt).isPresent();

        Employee e = opt.get();
        assertThat(e.getFirstName()).isEqualTo("Ivan");

    }

    @Test
    void testFindByIdNotExists() {
        Optional<Employee> opt = employeeRepo.findById(999);
        assertThat(opt).isEmpty();

    }

    @Test
    void DeleteById() {
        employeeRepo.deleteById(2);
        List<Employee> list = employeeRepo.findAll();
        assertThat(list).hasSize(1);
        assertThat(employeeRepo.findById(2)).isEmpty();
    }

}
