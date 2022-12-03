package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.entity.Receiver;
import net.javaguides.springboot.model.EmployeeDto;
import net.javaguides.springboot.model.ReceiverDto;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.awt.*;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto mapToService(Employee employee);
    Employee DtoToEntity(EmployeeDto employeeDto);

}
