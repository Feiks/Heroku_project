package net.javaguides.springboot.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import net.javaguides.springboot.entity.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void saveEmployee(Employee employee) {
		LocalDateTime now = LocalDateTime.now();
		employee.setDate(now);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		employee.setUser(username);
		employee.getPaymentTransfer().setState(String.valueOf(State.InProcess));
		this.employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAll() {
		return employeeRepository.getAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public void deleteById(long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public String getUniqueCode() {
		int codeLength = 12;
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < codeLength; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String codeOutput = sb.toString();
		return codeOutput;
	}

	@Override
	public List<Employee> getAllByUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		return employeeRepository.getEmployeesByUser(username);
	}

	@Override
	public List<Employee> getAllTransactionsToMe() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		return employeeRepository.getEmployeesByReceiver_NameContainingIgnoreCase(username);
	}
}
