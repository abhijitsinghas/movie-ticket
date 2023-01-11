package com.abhijits.movieticket.domain.theatre;

import com.abhijits.movieticket.domain.booking.Booking;
import com.abhijits.movieticket.domain.enums.BookingStatus;
import com.abhijits.movieticket.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 07 January, 2023
 */
@Data
@Accessors(chain = true)
@Entity
@Table(indexes = {
        @Index(columnList = "booking_uuid"),
        @Index(columnList = "show_uuid, seat_uuid", unique = true)
})
public class Reservation {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private ZonedDateTime expiryTime;

    @ManyToOne
    @JoinColumn
    private Seat seat;

    @ManyToOne
    @JoinColumn
    private Show show;

    @ManyToOne
    @JoinColumn
    private Booking booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (!Objects.equals(uuid, that.uuid)) return false;
        if (status != that.status) return false;
        if (!Objects.equals(price, that.price)) return false;
        return Objects.equals(expiryTime, that.expiryTime);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (expiryTime != null ? expiryTime.hashCode() : 0);
        return result;
    }
}


