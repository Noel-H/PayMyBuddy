package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.MoneyTransactionWithBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyTransactionWithBankAccountRepository extends JpaRepository<MoneyTransactionWithBankAccount, Long> {
}
