package com.transaction;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.inject.Inject;

@SpringBootApplication
@EnableScheduling
public class UserTransactionApplication extends SpringBootServletInitializer {
	@Inject
	private Logger logger;

	public static void main(String[] args) {
		SpringApplication.run(UserTransactionApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(){
		return (args)-> {
			logger.info("------------------*----------------------------------");
			logger.info("|                                                   |");
			logger.info("|      Started User TransactionHistory Application  |");
			logger.info("|                                                   |");
			logger.info("------------------*----------------------------------");
		};
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
		return applicationBuilder.sources(UserTransactionApplication.class);
	}

}
