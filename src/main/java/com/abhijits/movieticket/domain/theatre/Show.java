package com.abhijits.movieticket.domain.theatre;

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
        @Index(columnList = "hall_uuid"),
        @Index(columnList = "movie_uuid")
})
public class Show {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private ZonedDateTime startTime;

    @Column(nullable = false)
    private ZonedDateTime endTime;

    @ManyToOne
    @JoinColumn
    private Hall hall;

    @ManyToOne
    @JoinColumn
    private Movie movie;

    @OneToMany(mappedBy = "show")
    private Set<Reservation> reservation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (!Objects.equals(uuid, show.uuid)) return false;
        if (!Objects.equals(startTime, show.startTime)) return false;
        return Objects.equals(endTime, show.endTime);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }
}
