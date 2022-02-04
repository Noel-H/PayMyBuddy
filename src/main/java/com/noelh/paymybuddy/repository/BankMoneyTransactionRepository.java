package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.BankMoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankMoneyTransactionRepository extends JpaRepository<BankMoneyTransaction, Long> {
}
