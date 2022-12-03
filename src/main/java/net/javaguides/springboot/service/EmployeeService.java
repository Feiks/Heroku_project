package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.model.EmployeeDto;

public interface EmployeeService {

	void saveEmployee(EmployeeDto employeeDto);

	List<Employee> getAll();

	Optional<Employee> getEmployeeById(long id);

    void deleteById(long id);

	String getUniqueCode();

	List<EmployeeDto> getAllByUser();

	List<EmployeeDto> getAllTransactionsToMe();

	void getPayment(EmployeeDto employeeDto);
}
