package com.abhijits.movieticket.domain.address;

import jakarta.persistence.*;
import lombok.*;
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
@Table( indexes = @Index(columnList = "city_uuid"))
public class Address {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String addressLine3;

    @ManyToOne(optional = false)
    @JoinColumn
    City city;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(uuid, address.uuid)) return false;
        if (!Objects.equals(addressLine1, address.addressLine1))
            return false;
        if (!Objects.equals(addressLine2, address.addressLine2))
            return false;
        return Objects.equals(addressLine3, address.addressLine3);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (addressLine1 != null ? addressLine1.hashCode() : 0);
        result = 31 * result + (addressLine2 != null ? addressLine2.hashCode() : 0);
        result = 31 * result + (addressLine3 != null ? addressLine3.hashCode() : 0);
        return result;
    }
}
