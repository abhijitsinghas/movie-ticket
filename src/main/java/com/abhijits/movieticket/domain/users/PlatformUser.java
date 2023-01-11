package com.abhijits.movieticket.domain.users;

import com.abhijits.movieticket.domain.address.Address;
import com.abhijits.movieticket.domain.booking.Booking;
import com.abhijits.movieticket.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class PlatformUser {

    @Id
    @Column(columnDefinition = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;

    @OneToOne
    private Address address;

    @OneToOne
    private Account account;

    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlatformUser that = (PlatformUser) o;

        if (!Objects.equals(uuid, that.uuid)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(phoneNo, that.phoneNo)) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNo != null ? phoneNo.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}

