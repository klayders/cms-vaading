package org.orglot.gosloto.admin.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class AppConfig {

  public final static Locale RU = new Locale("ru", "RU");
  public final static Locale EN = new Locale("en", "US");


  @Bean
  public LocaleResolver localeResolver() {

    SessionLocaleResolver localResolver=new SessionLocaleResolver();
    localResolver.setDefaultLocale(RU);
    return localResolver;
  }
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:i18n/messages", "classpath:i18n/messages_ru");
    messageSource.setCacheSeconds(60 * 60 * 24);
    return messageSource;
  }

}
