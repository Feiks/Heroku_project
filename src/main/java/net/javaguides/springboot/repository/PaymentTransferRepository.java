package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.entity.PaymentTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransferRepository extends JpaRepository<PaymentTransfer, Long> {

    PaymentTransfer findPaymentTransferByCodeAndAmount(String code,int amount);

}
