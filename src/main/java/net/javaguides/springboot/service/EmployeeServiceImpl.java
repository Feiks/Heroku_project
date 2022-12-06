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
        LocalDateTime now = LocalDateTime.now();
        //Instant вместо localDate
        employeeDto.setDate(now);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        employeeDto.setUser(username);
        employeeDto.getPaymentTransfer().setState(String.valueOf(State.InProcess));
        Employee employee = employeeMapper.DtoToEntity(employeeDto);
        employeeRepository.save(employee);
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Invalid"));

        if (employee.getUser().equals(username)) {
            employeeRepository.deleteById(id);
        }
    }

    @Override
    public String getUniqueCode() {
        int codeLength = 12;
        char[] sold = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = sold[random.nextInt(sold.length)];
            sb.append(c);
        }
        return sb.toString();
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
        if(username.equals("admin")){
            return employeeRepository.getAll().stream().map(employeeMapper::mapToService).collect(Collectors.toList());
        }
        List<Employee> getEmployeeByReceiverName = employeeRepository.getEmployeesByReceiver_NameContainingIgnoreCase(username);
        return getEmployeeByReceiverName.stream().map(employeeMapper::mapToService).collect(Collectors.toList());
    }

    @Override
    public void getPayment(EmployeeDto employeeDto) {
        EmployeeMapper employeeMapper = new EmployeeMapperImpl();
        Employee employee = employeeMapper.DtoToEntity(employeeDto);
        String code = employee.getPaymentTransfer().getCode();

        int amount = employee.getPaymentTransfer().getAmount();
        PaymentTransfer paymentTransfer = paymentTransferRepository.findPaymentTransferByCodeAndAmount(code, amount);
        if (paymentTransfer == null) {
            System.out.println("NOT FOUND");
            return;
        }
        if (code.equals(paymentTransfer.getCode()) &&
                amount == paymentTransfer.getAmount()) {
            employee.getPaymentTransfer().setState(String.valueOf(State.Taken));
            try {
                employeeRepository.updatePaymentTransferState(code);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public int PaymentCalculationofUSD() {
        return employeeRepository.paymentCalculationOfUSD();
    }

    @Override
    public void updatePayment(EmployeeDto employeeDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        if (employeeDto.getUser().equalsIgnoreCase(username)) {
            Employee employee = employeeMapper.DtoToEntity(employeeDto);
            try {
                employeeRepository.updatePayment(employee.getFirstName(), employee.getLastName(), employee.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
            try {
                paymentTransferRepository.updatePayment(employee.getPaymentTransfer().getAmount(), employee.getPaymentTransfer().getCode());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public int PaymentCalculationofEUR() {
        int sum = employeeRepository.PaymentCalculationofEUR();
        return sum;
    }

    @Override
    public int PaymentCalculationofKGS() {
        int sum = employeeRepository.PaymentCalculationofKGS();
        return sum;
    }

    @Override
    public EmployeeDto findPaymentByKeyWord(String keyword) {
        Employee employee = employeeRepository.findEmployeeByPaymentTransferCode(keyword);
        return employeeMapper.mapToService(employee);
    }

    private void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }


}
