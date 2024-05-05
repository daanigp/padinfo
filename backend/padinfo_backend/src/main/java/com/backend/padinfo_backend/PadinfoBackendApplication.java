package com.backend.padinfo_backend;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PadinfoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadinfoBackendApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public OpenAPI customOpenAPI()
	{
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("PADINFO API")
						.description("API para la app de PADINFO")
						.contact(new Contact()
								.name("Daniel García Pascual")
								.email("danielgarciapascual23@gmail.com")
								.url("https://github.com/daanigp/padinfo"))
						.version("1.0"));
	}
}
