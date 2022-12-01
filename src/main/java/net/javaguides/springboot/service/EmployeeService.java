package net.javaguides.springboot.service;

import java.util.List;
import java.util.Optional;

import net.javaguides.springboot.entity.Employee;

public interface EmployeeService {

	void saveEmployee(Employee employee);

	List<Employee> getAll();

	Optional<Employee> getEmployeeById(long id);

    void deleteById(long id);

	String getUniqueCode();

	List<Employee> getAllByUser();

	List<Employee> getAllTransactionsToMe();
}
