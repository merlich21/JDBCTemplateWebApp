package ru.skillfactory.mappers;

import ru.skillfactory.DTO.EmployeeInfoDTO;
import ru.skillfactory.entites.Employee;

public interface EmployeeMapper {

    // Преобразование entity + отдел → DTO
    EmployeeInfoDTO toDto(Employee employee, String departmentName);
    // Можно добавить вариант: из одного entity (если DTO не содержит departmentName)
    EmployeeInfoDTO toDto(Employee employee);

    // Обратное преобразование (DTO → Entity), если нужно
    Employee toEntity(EmployeeInfoDTO dto);

}
