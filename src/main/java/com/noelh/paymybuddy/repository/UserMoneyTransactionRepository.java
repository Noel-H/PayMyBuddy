package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.UserMoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMoneyTransactionRepository extends JpaRepository<UserMoneyTransaction, Long> {
}
