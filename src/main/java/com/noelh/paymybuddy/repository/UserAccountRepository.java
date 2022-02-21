package com.noelh.paymybuddy.repository;

import com.noelh.paymybuddy.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    UserAccount findByLoginMail(String loginMail);

    boolean existsUserAccountByLoginMail(String loginMail);

    UserAccount getUserAccountByLoginMail(String loginMail);
}
