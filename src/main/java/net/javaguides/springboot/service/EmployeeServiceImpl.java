package net.javaguides.springboot.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import net.javaguides.springboot.entity.PaymentTransfer;
import net.javaguides.springboot.entity.State;
import net.javaguides.springboot.mapper.EmployeeMapper;
import net.javaguides.springboot.model.EmployeeDto;
import net.javaguides.springboot.repository.PaymentTransferRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final PaymentTransferRepository paymentTransferRepository;
	private final EmployeeMapper employeeMapper;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, PaymentTransferRepository paymentTransferRepository, EmployeeMapper employeeMapper) {
		this.employeeRepository = employeeRepository;
		this.paymentTransferRepository = paymentTransferRepository;
		this.employeeMapper = employeeMapper;
	}

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
	public List<EmployeeDto> getAllByUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();

		List<Employee> employeesByUser = employeeRepository.getEmployeesByUser(username);
		return employeesByUser.stream().map(employeeMapper::mapToService).collect(Collectors.toList());
	}

	@Override
	public List<Employee> getAllTransactionsToMe() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		return employeeRepository.getEmployeesByReceiver_NameContainingIgnoreCase(username);
	}

	@Override
	public void getPayment(Employee employee) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		PaymentTransfer paymentTransfer = paymentTransferRepository.findPaymentTransferByCodeAndAmount(employee.getPaymentTransfer().getCode(),employee.getPaymentTransfer().getAmount());

		if(employee.getPaymentTransfer().getCode().equals(paymentTransfer.getCode()) &&
			employee.getPaymentTransfer().getAmount()==paymentTransfer.getAmount()){
			employee.getPaymentTransfer().setState(String.valueOf(State.Taken));
		}
	}


}
