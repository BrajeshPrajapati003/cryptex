package com.cryptex.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

/**
 * @ConfigurationPropertiesScan
 * Spring automatically scans the classpath for all classes annotated with @ConfigurationProperties
 * No need to register each properties class  manually
 * Great if we have multiple classes (JwtProperties, MailProperties, RedisProperties,
 * S3Properties, KafkaProperties, StorageProperties, etc.)
 */

/**
 * @EnableConfigurationProperties
 * Explicitly telling Spring: Register this configuration properties class
 * Useful if we have only one or two properties classes
 */

//@EnableConfigurationProperties(JwtProperties.class)
@EnableJpaAuditing
public class  AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
