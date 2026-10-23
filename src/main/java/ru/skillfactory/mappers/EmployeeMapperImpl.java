package ru.skillfactory.mappers;

import ru.skillfactory.DTO.EmployeeInfoDTO;
import ru.skillfactory.entites.Employee;

public class EmployeeMapperImpl {

    // Преобразование entity + отдел → DTO
    public static EmployeeInfoDTO toDto(Employee employee, String departmentName) {
        return new EmployeeInfoDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getAge(),
                departmentName
        );
    }

    // Можно добавить вариант: из одного entity (если DTO не содержит departmentName)
    public static EmployeeInfoDTO toDto(Employee employee) {
        return new EmployeeInfoDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getAge(),
                null  // departmentName не задан
        );
    }

    // Обратное преобразование (DTO → Entity), если нужно
    public static Employee toEntity(EmployeeInfoDTO dto) {
        return new Employee(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAge()
                // departmentId можно установить отдельно
        );
    }
}
