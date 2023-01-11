package com.abhijits.movieticket.repository.address;

import com.abhijits.movieticket.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface AddressRepository extends JpaRepository<Address, UUID> {
}
