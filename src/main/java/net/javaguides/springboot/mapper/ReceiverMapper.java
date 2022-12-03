package net.javaguides.springboot.mapper;

import net.javaguides.springboot.entity.PaymentTransfer;
import net.javaguides.springboot.entity.Receiver;
import net.javaguides.springboot.model.PaymentTransferDto;
import net.javaguides.springboot.model.ReceiverDto;
import org.mapstruct.Mapper;

public interface ReceiverMapper {
    ReceiverDto mapToService(Receiver receiver);
}
