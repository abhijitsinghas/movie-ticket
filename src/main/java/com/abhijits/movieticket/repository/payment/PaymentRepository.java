package com.abhijits.movieticket.repository.payment;

import com.abhijits.movieticket.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
