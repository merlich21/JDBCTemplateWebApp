package ru.skillfactory.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillfactory.DTO.EmployeeInfoDTO;
import ru.skillfactory.entites.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmployeeRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate=namedParameterJdbcTemplate;
    }


    @Override
    public List<Employee> findAll() {

        String sql = "SELECT id, first_name, last_name, age, department_id FROM Employees";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getInt("department_id")
                )
        );
    }

    @Override
    public Optional<Employee> findById(int id) {

        String sql = "SELECT id, first_name, last_name, age, department_id FROM Employees WHERE id = ?";

        try {
            Employee emp = jdbcTemplate.queryForObject(sql,
                    new Object[]{id},
                    (rs, rowNum) -> new Employee(
                            rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("age"),
                            rs.getInt("department_id")
                    )
            );
            return Optional.of(emp);
        } catch (EmptyResultDataAccessException e) {
            // Если запись не найдена, queryForObject выбросит исключение EmptyResultDataAccessException
            return Optional.empty();
        }
    }

//    @Override
//    public void save(Employee employee) {
//
//        String sql = "INSERT INTO Employees(id, first_name, last_name, age) VALUES (:id, :first, :last, :age)";
//// Используем NamedParameterJdbcTemplate для наглядности
////        MapSqlParameterSource params = new MapSqlParameterSource()
////                .addValue("id", employee.getId())
////                .addValue("first_name", employee.getFirstName())
////                .addValue("last_name", employee.getLastName())
////                .addValue("age", employee.getAge());
//
//        Map<String, Object> paramMap = new HashMap<>();
//
//        paramMap.put("id", employee.getId());
//        paramMap.put("first_name", employee.getFirstName());
//        paramMap.put("last_name", employee.getLastName());
//        paramMap.put("age", employee.getAge());
//
//        namedParameterJdbcTemplate.update(sql, paramMap);
//
//    }
//
//    @Override
//    public void update(Employee employee) {
//        String sql = "UPDATE Employees SET first = ?, last = ?, age = ? WHERE id = ?";
//        int rowsAffected = jdbcTemplate.update(
//                sql,
//                employee.getFirstName(),
//                employee.getLastName(),
//                employee.getAge(),
//                employee.getId()
//        );
//        if (rowsAffected == 0) {
//            throw new RuntimeException("Сотрудник с id=" + employee.getId() + " не найден");
//        }
//    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Employee> findByLastName(String lastName) {

        String sql = "SELECT id, first_name, last_name, age, department_id FROM Employees WHERE last = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{ lastName },
                (rs, rowNum) -> new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getInt("department_id")
                        )
                );
    }

    @Override
    public List<Employee> findByAgeGreater(int minAge) {

        String sql = "SELECT id, first_name, last_name, age, department_id FROM Employees WHERE age > ?";
        return jdbcTemplate.query(
                sql,
                new Object[] { minAge },
                (rs, rowNum) -> new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getInt("department_id")
                        )
                );
    }

    @Override
    public List<Employee> findByLastNameAndAgeRange(String lastName, int minAge, int maxAge) {

        String sql = "SELECT id, first_name, last_name, age, department_id FROM Employees " +
                "WHERE last = :last AND age BETWEEN :minAge AND :maxAge";

        Map<String, Object> map = new HashMap<>();
        map.put("last_name", lastName);
        map.put("minAge", minAge);
        map.put("maxAge", maxAge);

        return namedParameterJdbcTemplate.query(sql, map,
                (rs, rowNum) -> new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getInt("department_id")
                )
        );
    }

    @Override
    public List<EmployeeInfoDTO> findAllWithDepartment() {
        String sql = "SELECT e.id, e.first_name, e.last_name, e.age, d.name AS deptName " +
                "FROM Employees e JOIN Departments d ON e.department_id = d.id";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new EmployeeInfoDTO(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("deptName")
                )
        );
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == 0) {
            // вставка нового сотрудника
            String insertSql = "INSERT INTO Employees (first_name, last_name, age, department_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(insertSql,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getAge(),
                    employee.getDepartmentId()
            );
        } else {
            // обновление существующего
            String updateSql = "UPDATE Employees SET first_name = ?, last_name = ?, age = ?, department_id = ? WHERE id = ?";
            jdbcTemplate.update(updateSql,
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getAge(),
                    employee.getDepartmentId(),
                    employee.getId()
            );
        }
        return employee;
    }
}
