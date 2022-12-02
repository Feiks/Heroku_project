package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.model.EmployeeDto;

public interface EmployeeMapper {
    EmployeeDto mapToService(Employee employee);
}
