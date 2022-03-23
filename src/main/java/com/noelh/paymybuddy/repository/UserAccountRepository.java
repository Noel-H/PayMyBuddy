package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserAccount repository
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    /**
     * Find an UserAccount by a loginMail
     * @param loginMail used to find the UserAccount
     * @return an UserAccount
     */
    UserAccount findByLoginMail(String loginMail);

    /**
     * Check if an UserAccount exist by a loginMail
     * @param loginMail used to check
     * @return true if the UserAccount exist
     */
    boolean existsUserAccountByLoginMail(String loginMail);

    /**
     * Get an UserAccount by a loginMail
     * @param loginMail used to get the UserAccount
     * @return an UserAccount
     */
    UserAccount getUserAccountByLoginMail(String loginMail);
}
