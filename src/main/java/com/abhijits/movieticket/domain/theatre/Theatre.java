package com.abhijits.movieticket.domain.theatre;

import com.abhijits.movieticket.domain.address.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

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
@Table(indexes = {
        @Index(columnList = "address_uuid", unique = true)
})
public class Theatre {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "theatre")
    private Set<Hall> halls;

    public Theatre addHall(Hall hall) {
        if (halls == null) {
            halls = new HashSet<>();
        }
        halls.add(hall);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theatre theatre = (Theatre) o;

        if (!Objects.equals(uuid, theatre.uuid)) return false;
        return Objects.equals(name, theatre.name);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
