package net.javaguides.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.javaguides.springboot.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
   @Query(value = "Select * from employees", nativeQuery = true)
    List<Employee> getAll();

    @Query(value = "UPDATE payment_transfer SET state = 'TAKEN' WHERE code = ?;", nativeQuery = true)
    void updatePaymentTransferState(String code);

    List<Employee> getEmployeesByUser(String username);

    List<Employee> getEmployeesByReceiver_NameContainingIgnoreCase(String username);

    @Query(value = "SELECT SUM(amount) FROM payment_transfer WHERE currency = 'USD' and state = 'TAKEN' ",nativeQuery = true)
 int paymentCalculationOfUSD();


 @Query(value = "UPDATE employees set first_name = ?, last_name = ? WHERE  id = ? ",nativeQuery = true)
 void updatePayment(String firstName, String lastName, long id);

 @Query(value = "SELECT SUM(amount) FROM payment_transfer WHERE currency = 'EURO' and  state = 'TAKEN' ",nativeQuery = true)
 int PaymentCalculationofEUR();

 @Query(value = "SELECT SUM(amount) FROM payment_transfer WHERE currency = 'KGS' and state = 'TAKEN' ",nativeQuery = true)
 int PaymentCalculationofKGS();

 Employee findEmployeeByPaymentTransferCode(String keyword);
}
