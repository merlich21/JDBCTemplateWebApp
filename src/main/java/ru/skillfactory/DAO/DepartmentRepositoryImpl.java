package ru.skillfactory.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.skillfactory.entites.Department;

import java.util.List;
import java.util.Optional;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT id, name FROM Departments";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Department(
                        rs.getInt("id"),
                        rs.getString("name")
                )
        );
    }

    @Override
    public Optional<Department> findById(int id) {
        String sql = "SELECT id, name FROM Departments WHERE id = ?";
        try {
            Department dept = jdbcTemplate.queryForObject(sql, new Object[]{id},
                    (rs, rowNum) -> new Department(
                            rs.getInt("id"),
                            rs.getString("name")
                    )
            );
            return Optional.of(dept);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Department dept) {
        String sql = "INSERT INTO Departments(id, name) VALUES(?, ?)";
        jdbcTemplate.update(sql, dept.getId(), dept.getName());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM Departments WHERE id = ?", id);
    }

}
