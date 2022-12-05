package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.PaymentTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentTransferRepository extends JpaRepository<PaymentTransfer, Long> {

    PaymentTransfer findPaymentTransferByCodeAndAmount(String code,int amount);

    @Query(value = "Update payment_transfer SET amount = ? WHERE code = ?",nativeQuery = true)
    void updatePayment(int amount, String code);
}
