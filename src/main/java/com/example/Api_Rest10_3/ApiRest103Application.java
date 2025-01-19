package com.example.Api_Rest10_3;

import com.example.Api_Rest10_3.repository.VotoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApiRest103Application {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(ApiRest103Application.class, args);
		VotoRepository repository = context.getBean(VotoRepository.class);
		System.out.println(repository.count());
	}

}
