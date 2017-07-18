package hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(CustomerRepository repo) {
		
		
		return (args) -> {
			repo.save(new Customer("Matija", "Lukovic"));
			repo.save(new Customer("Marko", "Markovic"));
			repo.save(new Customer("Ilija", "Petkovic"));
			repo.save(new Customer("Nikola", "Lukovic"));
			repo.save(new Customer("Marko", "Lukovic"));
		};
	}
}
