package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.MoneyTransactionWithUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MoneyTransactionWithUserAccount repository
 */
@Repository
public interface MoneyTransactionWithUserAccountRepository extends JpaRepository<MoneyTransactionWithUserAccount, Long> {
}
