package com.abhijits.movieticket.domain.theatre;

import com.abhijits.movieticket.domain.enums.Genre;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.HashSet;
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
public class Movie {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int durationInMins;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private ZonedDateTime releaseDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "movie")
    private Set<Show> shows;

    public Movie addShow(Show show) {
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

        Movie movie = (Movie) o;

        if (durationInMins != movie.durationInMins) return false;
        if (!Objects.equals(uuid, movie.uuid)) return false;
        if (!Objects.equals(title, movie.title)) return false;
        if (!Objects.equals(description, movie.description)) return false;
        if (!Objects.equals(language, movie.language)) return false;
        if (!Objects.equals(releaseDate, movie.releaseDate)) return false;
        return genre == movie.genre;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + durationInMins;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        return result;
    }
}

