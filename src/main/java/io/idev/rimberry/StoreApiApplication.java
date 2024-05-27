package io.idev.rimberry;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
@ComponentScan(basePackages = { "io.idev.rimberry" })
public class StoreApiApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(StoreApiApplication.class, args);
	}
	
	// global
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
