package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.Employee;
import net.javaguides.springboot.entity.PaymentTransfer;
import net.javaguides.springboot.model.EmployeeDto;
import net.javaguides.springboot.model.PaymentTransferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTransferMapper {
    PaymentTransferDto mapToService(PaymentTransfer paymentTransfer);
}
