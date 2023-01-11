package com.abhijits.movieticket.repository.user;

import com.abhijits.movieticket.domain.users.Account;
import com.abhijits.movieticket.domain.users.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by   : Abhijit Singh
 * On           : 08 January, 2023
 */
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
