package com.abhijits.movieticket.domain.theatre;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
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
        @Index(columnList = "theatre_uuid")
})
public class Hall {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int totalSeats;

    @OneToMany(mappedBy = "hall")
    private Set<Seat> seats;

    @OneToMany(mappedBy = "hall")
    private Set<Show> shows;

    @ManyToOne
    @JoinColumn
    private Theatre theatre;

    public Hall addSeat(Seat seat) {
        if (seats == null) {
            seats = new HashSet<>();
        }
        seats.add(seat);
        totalSeats = seats.size();
        return this;
    }

    public Hall addShow(Show show) {
        if (shows == null) {
            shows = new HashSet<>();
        }
        shows.add(show);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hall hall = (Hall) o;

        if (totalSeats != hall.totalSeats) return false;
        if (!uuid.equals(hall.uuid)) return false;
        return name.equals(hall.name);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + totalSeats;
        return result;
    }
}
