package com.cryptex.auth;

import com.cryptex.common.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;


/**
 * @ComponentScan("com.cryptex")
 * In a multi-module/microservice setup it's too broad.
 * If we already had JPA auditing enabled somewhere,
 * and we likely added it again while configuring the broader component scan.
 *
 * THE ERROR: The bean 'jpaAuditingHandler' could not be registered.
 *          A bean with that name has already been defined.
 *
 * Reason:
 *
 */

/**
 * @ConfigurationPropertiesScan
 * Spring automatically scans the classpath for all classes annotated with @ConfigurationProperties
 * No need to register each properties class  manually
 * Great if we have multiple classes (JwtProperties, MailProperties, RedisProperties,
 * S3Properties, KafkaProperties, StorageProperties, etc.)
 */

/**
 * @EnableConfigurationProperties(JwtProperties.class)
 * Explicitly telling Spring: Register this configuration properties class
 * Useful if we have only one or two properties classes
 */

@SpringBootApplication
@ConfigurationPropertiesScan
@Import(GlobalExceptionHandler.class)
public class  AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
}
