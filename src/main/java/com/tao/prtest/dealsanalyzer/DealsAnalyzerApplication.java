package com.tao.prtest.dealsanalyzer;

import com.tao.prtest.dealsanalyzer.dal.models.PerCurrencyResult;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DealsAnalyzerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DealsAnalyzerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DealsAnalyzerApplication.class, args);
	}

}
