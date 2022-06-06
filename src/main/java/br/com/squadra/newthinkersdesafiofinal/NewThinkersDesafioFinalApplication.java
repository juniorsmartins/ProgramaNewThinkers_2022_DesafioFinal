package br.com.squadra.newthinkersdesafiofinal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class NewThinkersDesafioFinalApplication {

	@Value("${application.name}")
	private String applicationName;

	public static void main(String[] args) {
		SpringApplication.run(NewThinkersDesafioFinalApplication.class, args);
	}

}
