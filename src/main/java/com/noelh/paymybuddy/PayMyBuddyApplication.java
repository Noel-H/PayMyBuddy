package com.noelh.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * PayMyBuddyApplication class
 */
@SpringBootApplication
@EnableTransactionManagement
public class PayMyBuddyApplication {

    /**
     * start and run the application
     * @param args is the argument
     */
    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

}
