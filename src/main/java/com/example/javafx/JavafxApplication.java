package com.example.javafx;

import com.example.javafx.config.javafx.FXApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavafxApplication {


	public static void main(String[] args) {
//		SpringApplication.run(JavafxApplication.class, args);
		Application.launch(FXApplication.class, args);
	}

}
