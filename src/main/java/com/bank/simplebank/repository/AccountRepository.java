package com.bank.simplebank.repository;

import com.bank.simplebank.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Modifying
    @Query(value = "UPDATE account SET balance = :balance WHERE id = :id", nativeQuery = true)
    void changeBalance(Long id, BigDecimal balance);

}
