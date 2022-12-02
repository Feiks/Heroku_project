package net.javaguides.springboot.controller;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import net.javaguides.springboot.entity.Currency;
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
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("codeOutput", employeeService.getUniqueCode());
		return "new_employee";
	}

	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		// save employee to database
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}

	@GetMapping("/")
	public String getAllEmployee(Model model) {
		List<Employee> employeeList = employeeService.getAllTransactionsToMe();
		List<Employee> myTransaction = employeeService.getAllByUser();
		model.addAttribute("employeeList", employeeList);
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
	public String saveUpdatedEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}

	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value="id") long id, Model model){
		employeeService.deleteById(id);
		return "redirect:/";

	}


	@GetMapping("/goToPaymentPage")
	public String goToPaymentPage(){
		return "receiver";
	}

	@PostMapping("/getPayment")
	public String  getPayment(@ModelAttribute("employee") Employee employee){

//		employeeService.getPayment(employee);
		return "redirect:/";

	}
}