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
import net.javaguides.springboot.mapper.EmployeeMapperImpl;
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
	public void saveEmployee(EmployeeDto employeeDto) {
		if(employeeDto.getPaymentTransfer().getState()!=null) {
			if (employeeDto.getPaymentTransfer().getState().equals("TAKEN")) {
				System.out.println("You can not update , it is already taken");
				return;
			}
		}
		LocalDateTime now = LocalDateTime.now();
		employeeDto.setDate(now);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		employeeDto.setUser(username);
		employeeDto.getPaymentTransfer().setState(String.valueOf(State.InProcess));
		EmployeeMapper employeeMapper1 = new EmployeeMapperImpl();
		Employee employee = employeeMapper1.DtoToEntity(employeeDto);
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
	public List<EmployeeDto> getAllTransactionsToMe() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		List<Employee> getEmployeeByReceiverName = employeeRepository.getEmployeesByReceiver_NameContainingIgnoreCase(username);
		return  getEmployeeByReceiverName.stream().map(employeeMapper::mapToService).collect(Collectors.toList());
	}

	@Override
	public void getPayment(EmployeeDto employeeDto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		EmployeeMapper employeeMapper1 = new EmployeeMapperImpl();
		Employee employee = employeeMapper1.DtoToEntity(employeeDto);

		PaymentTransfer paymentTransfer = paymentTransferRepository.findPaymentTransferByCodeAndAmount(employee.getPaymentTransfer().getCode(),employee.getPaymentTransfer().getAmount());
		if(paymentTransfer==null){
			System.out.println("NOT FOUND");
			return;
		}
		if(employee.getPaymentTransfer().getCode().equals(paymentTransfer.getCode()) &&
			employee.getPaymentTransfer().getAmount()==paymentTransfer.getAmount()){
			employee.getPaymentTransfer().setState(String.valueOf(State.Taken));
			String code = employee.getPaymentTransfer().getCode();
			try {
				employeeRepository.updatePaymentTransferState(code);
			}
			catch (Exception e){
				System.out.println(e);
			}
		}
	}

	@Override
	public int PaymentCalculation() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails) principal).getUsername();
		int sum = employeeRepository.PaymentCalculation();
		return sum;
	}

	private void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}


}
