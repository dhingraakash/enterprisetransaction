package com.enterprisetransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication
public class EnterpriseTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseTransactionApplication.class, args);
	}

}
