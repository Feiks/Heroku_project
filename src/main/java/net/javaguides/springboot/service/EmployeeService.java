package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.model.EmployeeDto;

public interface EmployeeService {

	void saveEmployee(Employee employee);

	List<Employee> getAll();

	Optional<Employee> getEmployeeById(long id);

    void deleteById(long id);

	String getUniqueCode();

	List<EmployeeDto> getAllByUser();

	List<Employee> getAllTransactionsToMe();

	void getPayment(Employee employee);
}
