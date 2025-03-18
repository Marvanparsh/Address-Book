package com.example.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class AddressbookApplication {
	public static void main(String[] args) {
		SpringApplication.run(AddressbookApplication.class, args);
	}
}
