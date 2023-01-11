package com.abhijits.movieticket.domain.payment;

import com.abhijits.movieticket.domain.booking.Booking;
import com.abhijits.movieticket.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 07 January, 2023
 */
@Data
@Accessors(chain = true)
@Entity
public class Payment {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private UUID transactionId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne(mappedBy = "payment")
    private Booking booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (Double.compare(payment.amount, amount) != 0) return false;
        if (!Objects.equals(uuid, payment.uuid)) return false;
        if (!Objects.equals(transactionId, payment.transactionId))
            return false;
        return status == payment.status;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = uuid != null ? uuid.hashCode() : 0;
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}

