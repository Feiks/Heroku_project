package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.model.EmployeeDto;

public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public EmployeeDto mapToService(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setId(employee.getId());
        employeeDto.setId(employee.getId());
       return employeeDto;
    }
}
