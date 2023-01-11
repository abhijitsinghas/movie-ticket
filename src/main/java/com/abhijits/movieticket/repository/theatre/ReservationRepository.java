package com.abhijits.movieticket.repository.theatre;

import com.abhijits.movieticket.domain.theatre.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Set<Reservation> findAllByShow_UuidAndSeat_UuidIn(UUID showUuid, Set<UUID> seats);

    @Modifying
    @Query("delete from Reservation where uuid in (:uuids)")
    void deleteByUuids(Set<UUID> uuids);

}
