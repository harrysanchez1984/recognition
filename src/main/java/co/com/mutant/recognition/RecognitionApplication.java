package co.com.mutant.recognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("co.com.mutant.recognition")
@EnableAsync
public class RecognitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecognitionApplication.class, args);
	}

}
