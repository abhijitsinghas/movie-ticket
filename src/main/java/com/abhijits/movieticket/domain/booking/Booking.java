package com.abhijits.movieticket.domain.booking;

import com.abhijits.movieticket.domain.payment.Payment;
import com.abhijits.movieticket.domain.enums.BookingStatus;
import com.abhijits.movieticket.domain.theatre.Reservation;
import com.abhijits.movieticket.domain.users.PlatformUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 07 January, 2023
 */
@Data
@Accessors(chain = true)
@Entity
@Table(indexes = {
        @Index(columnList = "payment_uuid")
})
public class Booking {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private ZonedDateTime bookedOn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(nullable = false)
    private int numberOfSeats;

    @OneToMany(mappedBy = "booking")
    private Set<Reservation> reservation;

    @OneToOne
    @JoinColumn
    private Payment payment;

    @ManyToOne(optional = false)
    @JoinColumn
    private PlatformUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (numberOfSeats != booking.numberOfSeats) return false;
        if (!Objects.equals(uuid, booking.uuid)) return false;
        if (!Objects.equals(bookedOn, booking.bookedOn)) return false;
        return status == booking.status;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (bookedOn != null ? bookedOn.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + numberOfSeats;
        return result;
    }
}
