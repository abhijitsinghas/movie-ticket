package com.abhijits.movieticket.service;

import com.abhijits.movieticket.domain.enums.PaymentStatus;
import com.abhijits.movieticket.domain.payment.Payment;
import com.abhijits.movieticket.repository.payment.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 10 January, 2023
 */
@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(double amount) {

        // Trigger Payment workflow for provider.

        return paymentRepository.save(
                new Payment()
                        .setAmount(amount)
                        .setStatus(PaymentStatus.PENDING)
                        .setTransactionId(UUID.randomUUID())
        );

    }

    public Payment paymentConfirmed(Payment payment) {
        return paymentRepository.save(payment.setStatus(PaymentStatus.COMPLETED));
    }



}
