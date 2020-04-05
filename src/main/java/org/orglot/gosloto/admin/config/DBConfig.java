package org.orglot.gosloto.admin.config;

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
}
