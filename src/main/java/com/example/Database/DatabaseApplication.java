package com.example.Database;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class DatabaseApplication {

	//private final DataSource dataSource;

//	//public DatabaseApplication(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}


//	@Override
//	public void run(String... args) throws Exception {
//		log.info("Database: " + dataSource.toString());
//		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
//		restTemplate.execute("select 1");
//	}
}
