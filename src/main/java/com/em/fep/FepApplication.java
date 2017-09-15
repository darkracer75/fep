package com.em.fep;

import com.em.fep.server.FepServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class FepApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(FepApplication.class, args);
		context.registerShutdownHook();
		FepServer fepServer = context.getBean(FepServer.class);
		fepServer.start();

	}
}
