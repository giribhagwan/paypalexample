package com.example.paypaldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaypaldemoApplication {

	static String clientId="AX8unzq7JxuCp_sHL04ZsrHqOTbwMNja64q4ME6YFXvmqLkvsOUuxC1QkQTgXXVKe0cTVtIxOoKsWHw_";
	static String secret="ECtVcDALikOdgqXH-BmUp--cCrtjyh2TFT4gyLlOWC9AbC_0eGjzxkyyWQRWyYdR-GwfUrylziJ_phb-";
	static String accessToken="access_token$sandbox$yffk4ygb4n6zy36b$491145dabea0d1806864779b0c5c45c5";

	public static void main(String[] args) {
		SpringApplication.run(PaypaldemoApplication.class, args);
	}

}
