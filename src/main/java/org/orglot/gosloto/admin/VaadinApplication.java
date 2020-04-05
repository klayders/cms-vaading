package org.orglot.gosloto.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(
    exclude = {
        SecurityAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        RedisAutoConfiguration.class
    })
public class VaadinApplication {

  public static void main(String[] args) {
    SpringApplication.run(VaadinApplication.class, args);
  }

}
