package ru.otus.hw12_hiber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.hw12_hiber.service.ScannerShopService;
import ru.otus.hw12_hiber.service.ShopService;

@SpringBootApplication
public class Hw12HiberApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Hw12HiberApplication.class, args);
		ScannerShopService scannerShopService = (ScannerShopService) context.getBean("scannerShopService");
		scannerShopService.run();
	}
}
