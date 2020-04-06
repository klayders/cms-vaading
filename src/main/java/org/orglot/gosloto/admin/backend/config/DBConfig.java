package org.orglot.gosloto.admin.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(
    {
        "org.orglot.gosloto.dao.managed.dao",
        "org.orglot.gosloto.components.entity",
        "org.orglot.gosloto.domain.achievement"
    }
)
@EnableTransactionManagement
public class DBConfig {

  //    @Bean
  //    public String dd(Repositories repositories){
  //        System.out.println(repositories);
  //        repositories.iterator().forEachRemaining(entity -> {
  //            System.out.println(entity.getName());
  //            for(var field : entity.getDeclaredFields()){
  //                System.out.println(field.getName());
  //            }
  //            System.out.println();
  //        });
  //
  //        return "memem";
  //    }
}
