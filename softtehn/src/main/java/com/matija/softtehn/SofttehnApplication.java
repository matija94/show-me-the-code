package com.matija.softtehn;

import com.matija.softtehn.model.repositories.DocumentFieldRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class SofttehnApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(SofttehnApplication.class, args);

		DocumentField df = new DocumentField(1, 1, "a", new Date());
		DocumentFieldRepository docFieldRepo = app.getBean(DocumentFieldRepository.class);
		docFieldRepo.save(df);

		System.out.println(docFieldRepo.findById(1l));
	}

}

