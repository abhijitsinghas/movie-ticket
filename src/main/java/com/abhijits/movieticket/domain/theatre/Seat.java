package com.abhijits.movieticket.domain.theatre;

import com.abhijits.movieticket.domain.enums.SeatType;
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
@Table(indexes = {
        @Index(columnList = "hall_uuid")
})
public class Seat {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private int rowNo;

    @Column(nullable = false)
    private int columnNo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType type;

    @ManyToOne
    @JoinColumn
    private Hall hall;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (rowNo != seat.rowNo) return false;
        if (columnNo != seat.columnNo) return false;
        if (!Objects.equals(uuid, seat.uuid)) return false;
        return type == seat.type;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + rowNo;
        result = 31 * result + columnNo;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

