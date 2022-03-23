package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BankAccount repository
 */
@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    /**
     * Check if the BankAccount exist by an iban
     * @param iban to check
     * @return true if the BankAccount exist
     */
    boolean existsBankAccountByIban(String iban);

    /**
     * get a BankAccount by an iban
     * @param iban used to get the BankAccount
     * @return the BankAccount
     */
    BankAccount getBankAccountByIban(String iban);
}
