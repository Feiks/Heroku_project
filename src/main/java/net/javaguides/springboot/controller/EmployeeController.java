package net.javaguides.springboot.controller;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import net.javaguides.springboot.entity.Currency;
import net.javaguides.springboot.model.EmployeeDto;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		EmployeeDto employeeDto = new EmployeeDto();
		model.addAttribute("employee", employeeDto);
		model.addAttribute("codeOutput", employeeService.getUniqueCode());
		return "new_employee";
	}



	@GetMapping("/")
	public String getAllEmployee(Model model) {
		List<EmployeeDto> employeeList = employeeService.getAllTransactionsToMe();
		List<EmployeeDto> myTransaction = employeeService.getAllByUser();
		int sum = employeeService.PaymentCalculation();
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("sum", sum);
		model.addAttribute("myTransaction", myTransaction);
		return "index";
	}

	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

		// get employee from the service
		Optional<Employee> employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	@PostMapping("/saveUpdatedEmployee")
	public String saveUpdatedEmployee(@ModelAttribute("employee") EmployeeDto employeeDto) {
		employeeService.saveEmployee(employeeDto);
		return "redirect:/";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value="id") long id, Model model){
		employeeService.deleteById(id);
		return "redirect:/";

	}


	@GetMapping("/goToPaymentPage")
	public String goToPaymentPage(Model model){
		EmployeeDto employeeDto = new EmployeeDto();
		model.addAttribute("employee", employeeDto);
		model.addAttribute("codeOutput", employeeService.getUniqueCode());
		return "receiver";
	}

	@PostMapping("/getPayment")
	public String  getPayment(Model model, @ModelAttribute("employee") EmployeeDto employeeDto){


		employeeService.getPayment(employeeDto);
		return "redirect:/";

	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") EmployeeDto employeeDto) {
		// save employee to database
		employeeService.saveEmployee(employeeDto);
		return "redirect:/";
	}
}